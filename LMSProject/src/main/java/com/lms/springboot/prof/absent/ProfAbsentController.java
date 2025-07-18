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

import com.lms.springboot.prof.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAbsentController
{
	@Autowired
	IProfAbsentService dao;
	
	@RequestMapping("/prof/absentboard.do")
	public String absentBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		
		int totalCount = dao.getStudentTotalCount(profDTO);
		int pageSize = 50; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		PagingUtil.paging(req, profDTO, model, totalCount, pageSize, blockPage, pageNum);
		
		ArrayList<ProfDTO> lists = dao.studentBoardListPage(profDTO);
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
	public ResponseEntity<String> insertData(@RequestBody ProfDTO profDTO, Model model, HttpServletRequest req) {
		
		String lecture_date = profDTO.getDate();
		String absent_states = profDTO.getData();
		
		String[] parts = absent_states.split("\\.");

		String absent_state = parts[0]; // the state of attendance
		String user_id = parts[1]; // user id
		String lectureCode = parts[2]; // lecture code
		
		
		dao.absentProcProf(user_id, absent_state, lecture_date, lectureCode);
		
		return new ResponseEntity<>("Data inserted successfully", HttpStatus.OK);

	}

	
}
