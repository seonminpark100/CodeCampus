package com.lms.springboot;

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
import com.lms.springboot.jdbc.ILectureService; 
import com.lms.springboot.jdbc.LectureBoardDTO;
import com.lms.springboot.jdbc.ParameterDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LectureBoardController {

	@Autowired
	ILectureService dao; 


	@GetMapping("/admin_lectureboard/lecturelist.do")
	public String lectureboardList(Model model, HttpServletRequest req,
			ParameterDTO parameterDTO) {

		int totalCount = dao.getTotalLectureCount(parameterDTO);
		int pageSize = 10;
		int blockPage = 2;

		int pageNum = (req.getParameter("pageNum")==null
			|| req.getParameter("pageNum").equals(""))
			? 1 : Integer.parseInt(req.getParameter("pageNum"));


		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		//계산의 결과는 DTO에 저장한다.
		parameterDTO.setStart(start);
		parameterDTO.setEnd(end);

		//View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);

		//DB에서 인출한 게시물의 목록을 Model에 저장
		ArrayList<LectureBoardDTO> lists = dao.listLecturePage(parameterDTO);
		model.addAttribute("lists", lists);

		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize,
				blockPage, pageNum,
				req.getContextPath()+"/admin_lectureboard/lecturelist.do?"); // 경로 수정
		model.addAttribute("pagingImg", pagingImg);


		return "admin_lectureboard/lecturelist";
	}

	@GetMapping("/admin_lectureboard/lecturewrite.do")
	public String boardWriteGet(Model model){
		return "admin_lectureboard/lecturewrite";
	}


	@PostMapping("/admin_lectureboard/lecturewrite.do")
	public String boardWritePost(LectureBoardDTO lectureBoardDTO){
		
		int result = dao.insertLecture(lectureBoardDTO); 
		return "redirect:/admin_lectureboard/lecturelist.do";
	}

	@RequestMapping("/admin_lectureboard/lectureview.do")
	public String boardView(Model model, LectureBoardDTO lectureBoardDTO) {
	    lectureBoardDTO = dao.viewLecture(lectureBoardDTO);
	    model.addAttribute("lectureBoardDTO", lectureBoardDTO); 
	    return "admin_lectureboard/lectureview";
	}

	@GetMapping("/admin_lectureboard/lectureedit.do")
	public String boardEditGet(Model model, LectureBoardDTO lectureBoardDTO){
		lectureBoardDTO = dao.viewLecture(lectureBoardDTO); 
		model.addAttribute("lectureBoardDTO", lectureBoardDTO); 
		return "admin_lectureboard/lectureedit";
	}


	@PostMapping("/admin_lectureboard/lectureedit.do")
	public String boardEditPost(LectureBoardDTO lectureBoardDTO){
		int result = dao.updateLecture(lectureBoardDTO); 

		return "redirect:/admin_lectureboard/lectureview.do?lecture_Idx="+lectureBoardDTO.getLecture_Idx(); 
	}

	@PostMapping("/admin_lectureboard/lecturedelete.do")
	public String boardDeletePost(@RequestParam("lecture_Idx") String lecture_Idx) {
		int result = dao.deleteLecture(lecture_Idx); 
		return "redirect:/admin_lectureboard/lecturelist.do";
	}
}