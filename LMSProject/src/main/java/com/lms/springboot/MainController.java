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
		return "home";
	}	

	@RequestMapping("/user/index.do")
	public String welcome1() {
		return "user/user";
	}
	
	@RequestMapping("/prof/index.do")
	public String welcome2() {
		return "prof/prof";
	}

	@RequestMapping("/admin/index.do")
	public String welcome3() {
		return "admin/admin";
	}
	
//	커스텀 로그인 페이지 매핑
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
	
	@RequestMapping("/myError.do")
	public String login2() {
		return "auth/error";
	}
	
	@RequestMapping("/denied.do")
	public String login3() {
		return "auth/denied";
	}
	
}
