package com.lms.springboot;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.jdbc.IUserService;
import com.lms.springboot.jdbc.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	IUserService dao;
	
	@GetMapping("/index.do")
	public String welcome(Principal principal, Model model, HttpSession session)
	{
		String user_id = principal.getName();
		session.setAttribute("user_id", user_id);
		return "user/index";
	}
	
	// 여기부터는 화면 확인용
	@GetMapping("/myLectureList.do")
	public String myLectureList(Model model, @AuthenticationPrincipal UserDetails ud ) {
		ArrayList<UserDTO> list = dao.selectAllMyLecture(ud.getUsername());
		model.addAttribute("list", list);
		return "user/myLectureList";
	}
	
	@GetMapping("/lectureList.do")
	public String lecture(Model model, UserDTO dto) {
		ArrayList<UserDTO> list = dao.selectLectureSessionList(dto.getLecture_code());
		model.addAttribute("list", list);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());

		return "user/lectureList";
	}
	
//	
//	@GetMapping("/lectureView.do")
//	public String lectureView() {
//		return "user/lectureView";
//	}
	@PostMapping("/lectureView.do")
	public String lectureView(Model model, UserDTO dto) {
		
		dto = dao.selectOneBoard(dto);
		model.addAttribute("dto", dto);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());		
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lectureView";
	}
	
	@GetMapping("/lectureBoardWrite.do")
	public String lectureBoardWrite() {
		return "user/lectureBoardWrite";
	}
	
	@GetMapping("/lectureBoardEdit.do")
	public String lectureBoardEdit() {
		return "user/lectureBoardEdit";
	}
	
	@GetMapping("/myPage.do")
	public String myPage(Model model, @AuthenticationPrincipal UserDetails ud) {
		UserDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "user/myPage";
	}
	
	@GetMapping("/infoEdit.do")
	public String infoEdit(Model model, @AuthenticationPrincipal UserDetails ud) {
		UserDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "user/infoEdit";
	}
	
	@PostMapping("/infoEdit.do")
	public String infoEditAction(UserDTO dto)
	{
		System.out.println("이름 : " + dto.getUser_name() + "  user_id : " + dto.getUser_id());
		int result = dao.updateUser(dto);
//		if(result == 1)
//		{
//			System.out.println("성공");
//		} else
//		{
//			System.out.println("실패");
//		}
		return "redirect:myPage.do";
	}
	
	@GetMapping("/resgistLecture.do")
	public String registLecture()
	{
		return "user/resgistLecture";
	}
}
