package com.lms.springboot.user;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.user.jdbc.IUserAssignmentService;
import com.lms.springboot.user.jdbc.UserAssignmentDTO;
import com.lms.springboot.user.jdbc.UserListParameterDTO;
import com.lms.springboot.utils.FileUtil;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/assignment")
public class UserAssignmentController
{
	@Autowired
	IUserAssignmentService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;
	
	@RequestMapping("/assignmentList.do")
	public String assignmentList(Model model, @ModelAttribute("message") String message, 
			HttpServletRequest req, UserListParameterDTO param, @AuthenticationPrincipal UserDetails ud) {
		param.setUser_id(ud.getUsername());
		int pageSize = this.propPageSize;
		int blockPage = this.propBlockPage;
		
		int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		param.setStart(start);
		param.setEnd(end);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		ArrayList<UserAssignmentDTO> list = dao.selectAllMyAssignmentList(param);
		int totalCount = list.size();
		
		maps.put("totalCount", totalCount);
		model.addAttribute("list", list);
		
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "assignmentList.do?");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("message", message);
		
		return "user/assignment/assignmentList";
	}
	
	@RequestMapping("assignmentSubmitWrite.do")
	public String assignmentWrite(Model model, UserAssignmentDTO dto, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		int result = dao.submitCheck(dto);
		if(result == 0) {
			if(dao.canSubmit(dto.getAssignment_idx()) == 1) {
				dto = dao.selectOneAssignment(dto.getAssignment_idx());
				dto.setAssignment_content(dto.getAssignment_content().replaceAll("\r\n", "<br/>"));
				model.addAttribute("dto", dto);
				return "user/assignment/assignmentSubmitWrite";	
			} else {
				redirectAttributes.addFlashAttribute("message", "강의 제출 기간이 아닙니다.");
				return "redirect:assignmentList.do";	
			}
		}
		return "redirect:assignmentSubmitView.do?assignment_idx=" + dto.getAssignment_idx();
	}
	
	@PostMapping("assignmentWriteAction.do")
	public String assignmentWriteAction(Model model, UserAssignmentDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setAssignment_idx(Integer.parseInt(req.getParameter("idx")));
		dto.setUser_id(ud.getUsername());

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Map<String, String> file = null;
					try
					{
						file = FileUtil.singleFileUpload(req, "assignmentFile", "Assignment");
					} catch (IOException | ServletException e)
					{
						e.printStackTrace();
						System.out.println("파일 업로드 실패 Rollback");
					}
					dto.setAssignment_ofile(file.get("oFile"));
					dto.setAssignment_sfile(file.get("sFile"));
					int result = dao.insertAssignmentSubmit(dto);
					
					if (result == 1)
						System.out.println("성공");
					else
						System.out.println("실패");
				}
			});
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("assignmentWriteAction RollBack");
		}

		return "redirect:assignmentSubmitView.do?assignment_idx=" + dto.getAssignment_idx();
	}
	
	@RequestMapping("assignmentSubmitView.do")
	public String assignmentSubmitView(Model model, UserAssignmentDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		dto.setAssignment_idx(Integer.parseInt(req.getParameter("assignment_idx")));
		dto = dao.selectOneAssignmentSubmit(dto);
		dto.setAssignment_content(dto.getAssignment_content().replaceAll("\r\n", "<br/>"));
		dto.setAssignment_content_s(dto.getAssignment_content_s().replaceAll("\r\n", "<br/>"));
		Date today = Date.valueOf(LocalDate.now());
		if(dao.selectOneAssignment(dto.getAssignment_idx()).getDeadline().after(today)) {
			model.addAttribute("canEdit", true);
		} else {
			model.addAttribute("canEdit", false);
		}
		model.addAttribute("dto", dto);
		model.addAttribute("file", FileUtil.getAssignFile(dto.getAssignment_ofile(), dto.getAssignment_sfile(), dto.getAssignment_submit_idx()));
		return "user/assignment/assignmentSubmitView";
	}
	
	@RequestMapping("assignmentSubmitEdit.do")
	public String assignmentEdit(Model model, UserAssignmentDTO dto, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		dto = dao.selectOneAssignmentSubmit(dto);
		
		model.addAttribute("dto", dto);
		return "user/assignment/assignmentSubmitEdit";
	}
	
	@PostMapping("assignmentSubmitEditAction.do")
	public String assignmentSubmitEditAction(UserAssignmentDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					if(!dto.getAssignment_sfile().equals(""))
					{
						FileUtil.deleteAssignmentFile(dto);
					}
					Map<String, String> file = null;
					try
					{
						file = FileUtil.singleFileUpload(req, "assignmentFile", "Assignment");
					} catch (IOException | ServletException e)
					{
						e.printStackTrace();
						System.out.println("파일 삭제 실패 RollBack");
					} 
					dto.setUser_id(ud.getUsername());
					dto.setAssignment_ofile(file.get("oFile"));
					dto.setAssignment_sfile(file.get("sFile"));
					int result = dao.updateAssignmentSubmit(dto);
					if(result != 1) {
						int error = 10 / 0;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("assignmentSubmitEditAction RollBack");
		}
		
		return "redirect:assignmentSubmitView.do?assignment_idx=" + dto.getAssignment_idx();
	}
	
	
	@RequestMapping("assignDownload.do")
	public void assignDownload(UserAssignmentDTO dto, HttpServletRequest req, HttpServletResponse resp,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		dto = dao.selectOneAssignmentSubmitBySubmitIdx(Integer.parseInt(req.getParameter("assignment_submit_idx")));	
		FileUtil.downloadFile(dto.getAssignment_sfile(), dto.getAssignment_ofile(), resp);
	}
}
