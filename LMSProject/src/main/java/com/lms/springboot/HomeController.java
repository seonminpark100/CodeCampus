package com.lms.springboot;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home(Principal principal, Model model) {
		if (principal != null) {
			model.addAttribute("user_id", principal.getName());
		}
		System.out.println("홈 페이지입니다.");
		return "home"; // src/main/webapp/WEB-INF/views/home.jsp 반환
	}

    @GetMapping("/myLogin.do") // GET 요청만 처리합니다.
    public String loginPage(
            Principal principal,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

    	 if (principal != null) {
             model.addAttribute("user_id", principal.getName());
             return "redirect:/";
         }

        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            System.out.println("로그인 실패: " + error);
        }
        if (logout != null) {
            model.addAttribute("logout", "성공적으로 로그아웃되었습니다.");
            System.out.println("로그아웃 성공: " + logout);
        }

        System.out.println("로그인 페이지로 이동합니다.");
        return "auth/login";
    }
    // WebMvcConfig는 별도 파일로 분리했으므로 여기에 없습니다.
}