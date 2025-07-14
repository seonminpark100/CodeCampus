package com.lms.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.MemberDTO;
import com.lms.springboot.service.MemberService;



@Controller
public class AdminController
{
	@GetMapping("/")
	public String home(){
		return "home";
	}	
	private final MemberService memberService; 
	public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }
	public String adminUserManagement(Model model) {
        model.addAttribute("users", memberService.getAllUsers()); // userInfoService -> memberService
        model.addAttribute("userCreateDto", new MemberDTO());
        return "admin/admin_user_management"; 
    }
	@PostMapping("/create") 
	 public String createUser(@ModelAttribute MemberDTO userCreateDto, RedirectAttributes redirectAttributes) {
       try {
           memberService.createUser(userCreateDto); 
           redirectAttributes.addFlashAttribute("message", "사용자 계정이 성공적으로 생성되었습니다."); 
           redirectAttributes.addFlashAttribute("messageType", "success");
       } catch (Exception e) {
           redirectAttributes.addFlashAttribute("message", "오류 발생: " + e.getMessage());
           redirectAttributes.addFlashAttribute("messageType", "danger");
       }
       return "redirect:/admin"; 
   }
}
