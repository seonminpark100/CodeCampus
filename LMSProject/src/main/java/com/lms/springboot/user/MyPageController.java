package com.lms.springboot.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.user.jdbc.IMyPageService;
import com.lms.springboot.user.jdbc.MyPageDTO;
import com.lms.springboot.utils.FileUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/myPage")
public class MyPageController
{
	@Autowired
	IMyPageService dao;
	
	@GetMapping("/myPage.do")
	public String myPage(Model model, @AuthenticationPrincipal UserDetails ud) {
		MyPageDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		System.out.println(dto.getSaveFile());
		return "user/myPage/myPage";
	}
	
	@GetMapping("/infoEdit.do")
	public String infoEdit(Model model, @AuthenticationPrincipal UserDetails ud) {
		MyPageDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "user/myPage/infoEdit";
	}
	
	@PostMapping("/infoEdit.do")
	public String infoEditAction(Model model, MyPageDTO dto, HttpServletRequest req) {
		try {
			String passwd =	PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dto.getUser_pw());
			passwd = passwd.substring(8);
			dto.setUser_pw(passwd);
			if(dto.getSaveFile() != null) {
				FileUtil.deleteOneFile(dto.getSaveFile());
			}
			Map<String, String> file = FileUtil.singleFileUpload(req, "profileImg", "User");
			dto.setOriginalFile(file.get("oFile"));
			dto.setSaveFile(file.get("sFile"));
			int result = dao.updateUser(dto);
			if(result == 1)	System.out.println("성공");
			else System.out.println("실패");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:myPage.do";
	}
}
