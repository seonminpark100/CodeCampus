package com.lms.springboot;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.jdbc.ILectureService;
import com.lms.springboot.jdbc.LectureDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	ILectureService dao;
	@RequestMapping("/index.do")
	public String welcome(Principal principal, Model model, HttpSession session)
	{
		String user_id = principal.getName();
		session.setAttribute("user_id", user_id);
		return "user/index";
	}
	
	
		
	// 여기부터는 화면 확인용
	@GetMapping("/myLectureList.do")
	public String myLectureList(Model model) {
		
		ArrayList<LectureDTO> lists = dao.list();
		model.addAttribute("lists", lists);
		return "user/myLectureList";
	}
	
	@GetMapping("/lecture.do")
	public String lecture(Model model, LectureDTO lecture) {
		lecture = dao.view(lecture.getLecture_id());
		model.addAttribute("lecture", lecture);
		return "user/lectureList";
	}
	
//	@GetMapping("/lectureList.do")
//	public String lectureList(Model model, LectureDTO lecture) {
//		lecture = dao.view(lecture.getLecture_id());
//		model.addAttribute("lecture", lecture);
//		return "user/lectureList";
//	}
	
	@GetMapping("/lectureView.do")
	public String lectureView() {
		return "user/lectureView";
	}
	
	@GetMapping("user/lectureBoardWrite.do")
	public String lectureBoardWrite() {
		return "user/lectureBoardWrite";
	}
	
	@GetMapping("/lectureBoardEdit.do")
	public String lectureBoardEdit() {
		return "user/lectureBoardEdit";
	}
	
	@GetMapping("/myPage.do")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("/infoEdit.do")
	public String infoEdit() {
		return "user/infoEdit";
	}
}
