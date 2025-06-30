package com.lms.springboot;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController
{
	@GetMapping("/")
	public String home()
	{
		return "auth/myLogin";
	}	
		
	@RequestMapping("/myLogin.do")
	public String login1(Principal principal, Model model) {
		try
		{
			String user_id = principal.getName();
			model.addAttribute("user_id", user_id);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "auth/login";
	}
	
	@RequestMapping("/user/index.do")
	public String welcome(Principal principal, Model model)
	{
		String user_id = principal.getName();
		model.addAttribute("user_id", user_id);
		return "user/index";
	}
		
	// 여기부터는 화면 확인용
	@GetMapping("user/myLectureList.do")
	public String myLectureList() {
		return "user/myLectureList";
	}
	
	@GetMapping("user/lecture.do")
	public String lecture() {
		return "user/lecture";
	}
	
	@GetMapping("user/lectureList.do")
	public String lectureList() {
		return "user/lectureList";
	}
	@GetMapping("user/lectureView.do")
	public String lectureView() {
		return "user/lectureView";
	}
	
	@GetMapping("user/lectureResource.do")
	public String lectureResource() {
		return "user/lectureResource";
	}
	
	@GetMapping("user/lectureResourceView.do")
	public String lectureResourceView() {
		return "user/lectureResourceView";
	}
	
	@GetMapping("user/lectureBoard.do")
	public String lectureBoard() {
		return "user/lectureBoard";
	}
	
	@GetMapping("user/lectureBoardView.do")
	public String lectureBoardView() {
		return "user/lectureBoardView";
	}
	
	@GetMapping("user/lectureBoardWrite.do")
	public String lectureBoardWrite() {
		return "user/lectureBoardWrite";
	}
	
	@GetMapping("user/lectureBoardEdit.do")
	public String lectureBoardEdit() {
		return "user/lectureBoardEdit";
	}
	
	@GetMapping("user/myPage.do")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("user/surveyList.do")
	public String surveyList() {
		return "user/surveyList";
	}
	
	@GetMapping("user/surveyView.do")
	public String surveyView() {
		return "user/surveyView";
	}
	
	@GetMapping("/passCheck.do")
	public String passCheck() {
		return "auth/passCheck";
	}
	
	@GetMapping("user/infoEdit.do")
	public String infoEdit() {
		return "user/infoEdit";
	}
}
