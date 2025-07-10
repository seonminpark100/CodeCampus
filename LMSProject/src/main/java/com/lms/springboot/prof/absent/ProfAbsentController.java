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
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.prof.IProfService;
import com.lms.springboot.prof.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAbsentController
{
	@Autowired
	IProfAbsentService dao;
//	출석
	@RequestMapping("/prof/absentboard.do")
	public String absentBoardList(@RequestParam("lectureCode") String lecture_code, Model model, HttpServletRequest req, ProfDTO profDTO) {
		profDTO.setLecture_code(lecture_code);
		
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
				req.getContextPath()+"/prof/absentboard.do?lectureCode="+lecture_code+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lecture_code);
		
		return "prof/absentBoard/absentBoardList";
	}
	
	@PostMapping("/prof/absentProc.do")
	public ResponseEntity<String> insertData(@RequestBody MyData data, @RequestBody MyData_2 data_2, Model model, HttpServletRequest req) {
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		
		String lectureCode = req.getParameter("lectureCode");
//		String user_id = req.getParameter("absent_state");
		String absent_state = data.getData();
		String user_id = data_2.getData_2();

		System.out.println("lectureCode: " + lectureCode);
		System.out.println("user_id: " + user_id);
		System.out.println("absent_state: " + absent_state);
		
		return new ResponseEntity<>("Data inserted successfully", HttpStatus.OK);

	}
	static class MyData {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
	
	static class MyData_2 {
		private String data_2;
		
		public String getData_2() {
			return data_2;
		}
		
		public void setData_2(String data_2) {
			this.data_2 = data_2;
		}
	}
}
