package com.lms.springboot.prof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.jdbc.IProfService;
import com.lms.springboot.jdbc.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfController
{
	@Autowired
	IProfService dao;	
//	강의 메인 화면
	@GetMapping("/prof/submain.do")
	public String profSubMain(@RequestParam("lectureId") String lectureId, Model model) {
		model.addAttribute("lectureId", lectureId);
		System.out.println("lectureId: " + lectureId);
		return "prof/submain";
	}
	
//	수강생 리스트
	@GetMapping("/prof/studentList.do")
	public String studentList(@RequestParam("lectureId") String lectureId,HttpServletRequest req, Model model, ProfDTO profDTO) {

		int totalCount = dao.getStudentTotalCount(profDTO, lectureId);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		//현제 페이지에 출력할 게시물의 구간을 계산한다. 
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		//계산의 결과는 DTO에 저장한다. 
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		//View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장 
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		ArrayList<ProfDTO> lists = dao.studentBoardListPage(profDTO, lectureId);
		model.addAttribute("lists", lists);
		
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/noticeboard.do?");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureId", lectureId);
		return "prof/studentBoard/studentList";
	}
	
//	강의 업로드 관리
	@GetMapping("/prof/lectureboard.do")
	public String letureBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) 
	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
		
		int totalCount = dao.getLectureTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		//현제 페이지에 출력할 게시물의 구간을 계산한다. 
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		//계산의 결과는 DTO에 저장한다. 
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		//View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장 
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		ArrayList<ProfDTO> lists = dao.lectureBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/noticeboard.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		
		return "prof/lectureBoard/lectureBoardList";       
	}	
	
//	공지사항
	@RequestMapping("/prof/noticeboard.do")
	public String noticeBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) 
	{
		int totalCount = dao.getNoticeBoardTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		//현제 페이지에 출력할 게시물의 구간을 계산한다. 
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		//계산의 결과는 DTO에 저장한다. 
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		//View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장 
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		ArrayList<ProfDTO> lists = dao.noticeBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/noticeboard.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		
		return "prof/noticeBoard/noticeBoardList";       
	}
	
	
//	출석
	@RequestMapping("/prof/absentboard.do")
	public String absentBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) {
		
		int totalCount = dao.getAbsentBoardTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		ArrayList<ProfDTO> lists = dao.absentBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/noticeboard.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		return "prof/absentBoard/absentBoardList";
	}

}
