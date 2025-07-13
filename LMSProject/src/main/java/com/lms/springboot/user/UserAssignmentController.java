package com.lms.springboot.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String assignmentList(Model model, HttpServletRequest req, UserListParameterDTO param, 
			@AuthenticationPrincipal UserDetails ud) {
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
		return "user/assignment/assignmentList";
	}
	
	@RequestMapping("assignmentSubmitWrite.do")
	public String assignmentWrite(Model model, UserAssignmentDTO dto, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		int result = dao.submitCheck(dto);
		if(result == 0) {
			dto = dao.selectOneAssignment(dto.getAssignment_idx());
			dto.setAssignment_content(dto.getAssignment_content().replaceAll("\r\n", "<br/>"));
			model.addAttribute("dto", dto);
			return "user/assignmentSubmitWrite";	
		}
		return "redirect:assignmentSubmitView.do?assignment_idx=" + dto.getAssignment_idx();
	}
	
	@PostMapping("assignmentWriteAction.do")
	public String assignmentWriteAction(Model model, UserAssignmentDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setAssignment_idx(Integer.parseInt(req.getParameter("idx")));
		dto.setUser_id(ud.getUsername());

		try {
			Map<String, String> file = FileUtil.singleFileUpload(req, "assignmentFile", "Assignment");
			dto.setAssignment_ofile(file.get("oFile"));
			dto.setAssignment_sfile(file.get("sFile"));
			int result = dao.insertAssignmentSubmit(dto);
			
			if (result == 1)
				System.out.println("성공");
			else
				System.out.println("실패");
		} catch (IOException | ServletException e){
			e.printStackTrace();
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
			if(!dto.getAssignment_sfile().equals(""))
			{
				FileUtil.deleteAssignmentFile(dto);
			}
			Map<String, String> file = FileUtil.singleFileUpload(req, "assignmentFile", "Assignment"); 
			dto.setUser_id(ud.getUsername());
			dto.setAssignment_ofile(file.get("oFile"));
			dto.setAssignment_sfile(file.get("sFile"));
			int result = dao.updateAssignmentSubmit(dto);
			if(result != 1) {
				int error = 10 / 0;
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
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
