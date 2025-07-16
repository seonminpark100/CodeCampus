package com.lms.springboot.prof;

import java.util.ArrayList;
import java.util.Map;

import com.lms.springboot.user.jdbc.MyPageDTO;
import com.lms.springboot.utils.FileUtil;
import com.lms.springboot.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfController
{
	@Autowired
	IProfService dao;
	
	@RequestMapping("/prof/index.do")
	public String welcome2(@AuthenticationPrincipal UserDetails user, Model model, ProfDTO profDTO) {
		String user_id = user.getUsername();
		
		
		ArrayList<ProfDTO> lists = dao.userList(profDTO, user_id);
		model.addAttribute("lists", lists);
		model.addAttribute("user_id", user_id);

		return "prof/prof";
	}
//	Lecture main page
	@GetMapping("/prof/submain.do")
	public String profSubMain(HttpServletRequest req, Model model, NoticeBoardDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		model.addAttribute("lectureCode" , lectureCode);
		
		ArrayList<ProfDTO> lists = dao.selectNoticeBoardList(DTO);
		ArrayList<ProfDTO> lists_c = dao.selectCommunityList(DTO);
		model.addAttribute("lists", lists);
		model.addAttribute("lists_c", lists_c);
		
		return "prof/submain";
	}
	
//	Students List
	@GetMapping("/prof/studentList.do")
	public String studentList(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getStudentTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		Paging.paging(req, model, totalCount, pageSize, blockPage, pageNum);
		
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		// Saved DB dates at Model Object
		ArrayList<ProfDTO> lists = dao.studentBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		model.addAttribute("lectureCode", lectureCode);
		// Page Number Stuff
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/studentList.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		
		return "prof/studentBoard/studentList";
	}

	@RequestMapping("/prof/mypage.do")
	public String mypage(Model model, @AuthenticationPrincipal UserDetails ud) {
		
		MypageDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "prof/myPage/mypage";
	}
	
	@GetMapping("/prof/infoEdit.do")
	public String infoEdit(Model model, @AuthenticationPrincipal UserDetails ud) {
		MypageDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "prof/myPage/infoEdit";
	}
	
	@PostMapping("/prof/infoEdit.do")
	public String infoEditAction(Model model, MypageDTO dto, HttpServletRequest req) {
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
			dao.updateUser(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:mypage.do";
	}
	

}
