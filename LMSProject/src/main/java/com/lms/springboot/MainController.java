package com.lms.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController
{
	@GetMapping("/")
	public String home()
	{
		return "mainPage";
	}	
	
	@GetMapping("/loginPage.do")
	public String loginPage() {
		return "auth/loginPage";
	}
	
	@GetMapping("/loginAction.do")
	public String loginAction() {
		
		return "mainPage";
	}
	
	// 여기부터는 화면 확인용
	@GetMapping("/myLectureList.do")
	public String myLectureList() {
		return "guest/myLectureList";
	}
	
	@GetMapping("/lecture.do")
	public String lecture() {
		return "guest/lecture";
	}
	
	@GetMapping("/lectureList.do")
	public String lectureList() {
		return "guest/lectureList";
	}
	@GetMapping("/lectureView.do")
	public String lectureView() {
		return "guest/lectureView";
	}
	
	@GetMapping("/lectureResource.do")
	public String lectureResource() {
		return "guest/lectureResource";
	}
	
	@GetMapping("/lectureResourceView.do")
	public String lectureResourceView() {
		return "guest/lectureResourceView";
	}
	
	@GetMapping("/lectureBoard.do")
	public String lectureBoard() {
		return "guest/lectureBoard";
	}
	
	@GetMapping("/lectureBoardView.do")
	public String lectureBoardView() {
		return "guest/lectureBoardView";
	}
	
	@GetMapping("/lectureBoardWrite.do")
	public String lectureBoardWrite() {
		return "guest/lectureBoardWrite";
	}
	
	@GetMapping("/lectureBoardEdit.do")
	public String lectureBoardEdit() {
		return "guest/lectureBoardEdit";
	}
	
	@GetMapping("/myPage.do")
	public String myPage() {
		return "guest/myPage";
	}
	
	@GetMapping("/surveyList.do")
	public String surveyList() {
		return "guest/surveyList";
	}
	
	@GetMapping("/surveyView.do")
	public String surveyView() {
		return "guest/surveyView";
	}
	
	@GetMapping("/passCheck.do")
	public String passCheck() {
		return "auth/passCheck";
	}
	
	@GetMapping("/infoEdit.do")
	public String infoEdit() {
		return "guest/infoEdit";
	}
}
