<<<<<<< HEAD
// MainController.java (수정 후)
package com.lms.springboot;

import java.security.Principal;
=======
package com.lms.springboot;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
>>>>>>> origin/master
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
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

    @RequestMapping("/myError.do")
    public String login2() {
        return "auth/error";
    }

    @RequestMapping("/denied.do")
    public String login3() {
        return "auth/denied";
    }

    @RequestMapping("/myLogin.do")
    public String login1(Principal principal, Model model) {
        try {
            String userId = null;
            if (principal != null) {
                userId = principal.getName();
                model.addAttribute("userId", userId);
            } else {
                System.out.println("로그인되지 않은 사용자가 /myLogin.do 에 접근했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "auth/login";
    }
}
=======
import com.lms.springboot.prof.IProfService;
import com.lms.springboot.prof.ProfDTO;

@Controller
public class MainController
{
	
	@Autowired
	IProfService dao;
	
	@GetMapping("/")
	public String home()
	{
		return "home";
	}	

	@RequestMapping("/user/index.do")
	public String welcome1() {
		return "user/user";
	}
	


	@RequestMapping("/admin/index.do")
	public String welcome3() {
		return "admin/admin";
	}
	
//	Custom Login Page
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
>>>>>>> origin/master
