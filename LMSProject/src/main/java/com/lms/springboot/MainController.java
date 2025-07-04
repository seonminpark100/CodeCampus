// MainController.java (수정 후)
package com.lms.springboot;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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