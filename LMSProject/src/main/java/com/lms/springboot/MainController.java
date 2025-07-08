package com.lms.springboot;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController
{
//	@Autowired
//	ILectureService dao;
	
	@GetMapping("/")
	public String home()
	{
		return "auth/myLogin";
		
	}	
		
	@RequestMapping("/myLogin.do")
	public String login1(Principal principal, Model model, HttpSession session) {
		try
		{
			String user_id = principal.getName();
			model.addAttribute("user_id", user_id);
			
		} catch (Exception e)
		{
			System.out.println("로그인 전입니다.");
		}
		return "auth/myLogin";
	}
		
	// 로그인 오류 관련
	@RequestMapping("/myError.do")
	public String login2()
	{
		return "auth/error";
	}
	
	//권한이 부족한 경우 
	@RequestMapping("/denied.do")
	public String login3()
	{
		return "auth/denied";
	}
	
	@GetMapping("/passCheck.do")
	public String passCheck() {
		return "auth/passCheck";
	}
}
