package com.lms.springboot.prof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.jdbc.IProfService;
import com.lms.springboot.jdbc.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAssignmentController
{
	@Autowired
	IProfService dao;
	
//	과제 목록 관리(교수)
	@GetMapping("/prof/assignmentList.do")
	public String assignmentList(@RequestParam("lectureCode") String lecture_code, HttpServletRequest req, Model model, ProfDTO profDTO) {
		profDTO.setLecture_code(lecture_code);
		
		int totalCount = dao.getAssignmentTotalCount(profDTO);
		int pageSize = 3; 
		int blockPage = 20; 
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
		ArrayList<ProfDTO> lists = dao.assignmentBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/assignmentList.do?lectureCode="+lecture_code+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lecture_code);
		return "prof/assignmentBoard/assignmentList";
	}
//	과제 업로드(교수) - 화면보기
	@RequestMapping("/prof/assignmentUpload.do")
	public String lectureUpload (@RequestParam("lectureCode") String lectureCode, Model model) {
		model.addAttribute("lectureCode", lectureCode);
		return "prof/assignmentBoard/assignmentUpload";
	}
//	과제 업로드(교수) - action
	@PostMapping("/prof/assignmentUpload.do")
	public String lectureUploadProc(Model model, HttpServletRequest req) {
		String title = req.getParameter("assignment_title");
		String content = req.getParameter("assignment_content");
		String deadline = req.getParameter("deadline");
		String lectureCode = req.getParameter("lectureCode");
		
		int result_prof = dao.lectureUploadProcProf(title, content, deadline, lectureCode);
		return "redirect:assignmentList.do?lectureCode="+lectureCode;
	}
//	과제 상세보기(교수)
	@RequestMapping("/prof/assignmentView.do")
	public String assignmentView(@RequestParam("lectureCode") String lectureCode, Model model, ProfDTO profDTO) {
		profDTO = dao.assignmentView(profDTO);
		profDTO.setAssignment_content(profDTO.getAssignment_content().replace("\r\n", "<br/>"));
		model.addAttribute("profDTO", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/assignmentBoard/assignmentView";
	}
//	과제 수정하기(교수) - 내용가져오기
	@GetMapping("/prof/assignmentEdit.do")
	public String assignmentEditGet(Model model, ProfDTO profDTO) {
		profDTO = dao.assignmentView(profDTO);
		model.addAttribute("profDTO", profDTO);
		return "prof/assignmentBoard/assignmentEdit";
	}
//	과제 수정하기(교수) - action
	@PostMapping("/prof/assignmentEdit.do")
	public String assignmentEditPost(Model model, ProfDTO profDTO) {
		int result = dao.assignmentEdit(profDTO);
		return "redirect:assignmentView.do?lectureCode=" + profDTO.getLecture_code()+"&assignment_idx=" +profDTO.getAssignment_idx();
	}
//	과제 삭제하기(교수)
	@PostMapping("/prof/assignmentDelete.do")
	public String assignmentDelete(HttpServletRequest req, Model model) {
		int result = dao.assignmentDelete(req.getParameter("assignment_idx"));
		String lectureCode = req.getParameter("lectureCode");
		return "redirect:assignmentList.do?lectureCode="+ lectureCode;
	}
}
