package com.lms.springboot.prof.absent;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.prof.Paging;
import com.lms.springboot.prof.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAbsentController
{
	@Autowired
	IProfAbsentService dao;
	
	@RequestMapping("/prof/absentboard.do")
	public String absentBoardList(Model model, HttpServletRequest req, AbsentDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		
		int totalCount = dao.getStudentTotalCount(DTO);
		int pageSize = 50; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		Paging.paging(req, model, totalCount, pageSize, blockPage, pageNum);
		
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		
		DTO.setStart(start);
		DTO.setEnd(end);
		
		ArrayList<ProfDTO> lists = dao.studentBoardListPage(DTO);
		model.addAttribute("lists", lists);
		
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/absentboard.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/absentBoard/absentBoardList";
	}
	
	@PostMapping("/prof/absentProc.do")
	public ResponseEntity<String> insertData(@RequestBody AbsentDTO DTO, Model model, HttpServletRequest req) {
		
		String lecture_date = DTO.getDate();
		String absent_states = DTO.getData();
		
		String[] parts = absent_states.split("\\.");

		String absent_state = parts[0]; // the state of attendance
		String user_id = parts[1]; // user id
		String lectureCode = parts[2]; // lecture code
		
		
		dao.absentProcProf(user_id, absent_state, lecture_date, lectureCode);
		
		return new ResponseEntity<>("Data inserted successfully", HttpStatus.OK);

	}

	
}
