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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.user.jdbc.IRegistLectureService;
import com.lms.springboot.user.jdbc.RegistLectureDTO;
import com.lms.springboot.user.jdbc.UserListParameterDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/registLecture")
public class RegistLectureController
{
	@Autowired
	IRegistLectureService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;
	
	@GetMapping("/registLecture.do")
	public String registLecture(Model model, @ModelAttribute("message") String message, HttpServletRequest req, UserListParameterDTO param) {
		int totalCount = dao.getTotalLectureCount(param);
		
		int pageSize = this.propPageSize;
		int blockPage = this.propBlockPage;
		
		int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		param.setStart(start);
		param.setEnd(end);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		ArrayList<RegistLectureDTO> list = dao.selectAllLecturePage(param);
		model.addAttribute("list", list);
		
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "registLecture.do?");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("message", message);
		
		return "user/regist/registLecture";
	}
	
	@PostMapping("/registLecture.do")
	public String registLectureAction(RedirectAttributes redirectAttributes, HttpServletResponse resp, RegistLectureDTO dto,
			@AuthenticationPrincipal UserDetails ud) throws IOException {
		dto.setUser_id(ud.getUsername());
		if(dao.duplicationEnrollCheck(dto) == 0) {	// 이미 수강중인 강의가 아닐때
			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						int result = dao.insertEnroll(dto);
						if(result == 1) redirectAttributes.addFlashAttribute("message", "수강신청 성공!!");
						else {
							int error = 10 / 0;
						}
					}
				});
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "수강신청 실패...");
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "이미 수강중인 강의입니다.");
		}
		return "redirect:registLecture.do";
	}
}
