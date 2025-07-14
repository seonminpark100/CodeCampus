package com.lms.springboot.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.user.jdbc.IUserService;
import com.lms.springboot.user.jdbc.UserDTO;
import com.lms.springboot.user.jdbc.UserFileDTO;
import com.lms.springboot.utils.FileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	IUserService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;
	
	@GetMapping("/index.do")
	public String welcome(Principal principal, Model model, HttpSession session) {
		String user_id = principal.getName();
		session.setAttribute("user_id", user_id);
		return "user/index";
	}

	@RequestMapping("download.do")
	public void download(UserFileDTO dto, HttpServletRequest req, HttpServletResponse resp) {
		String fileName = req.getParameter("fileName");
		
		dto = dao.selectOneFile(fileName);
		FileUtil.downloadFile(dto.getSFile(), dto.getOFile(), resp);
	}	
}
