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
}
