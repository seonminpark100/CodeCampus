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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.springboot.jdbc.ILectureService; 
import com.lms.springboot.jdbc.LectureBoardDTO;
import com.lms.springboot.jdbc.ParameterDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LectureBoardController {

	@Autowired
	ILectureService dao; 


	@GetMapping("/lectureboard/lecturelist.do")
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
				req.getContextPath()+"/lectureboard/lecturelist.do?"); // 경로 수정
		model.addAttribute("pagingImg", pagingImg);


		return "lectureboard/lecturelist";
	}

	@GetMapping("/lectureboard/lecturewrite.do")
	public String boardWriteGet(Model model)
	{
		return "lectureboard/lecturewrite";
	}


	@PostMapping("/lectureboard/lecturewrite.do")
	public String boardWritePost(LectureBoardDTO lectureBoardDTO) 
	{
		
		int result = dao.insertLecture(lectureBoardDTO); // 메서드명 변경 및 DTO 객체 전달
		return "redirect:/lectureboard/lecturelist.do";
	}

	@RequestMapping("/lectureboard/lectureview.do")
	public String boardView(Model model, LectureBoardDTO lectureBoardDTO) {
	    lectureBoardDTO = dao.viewLecture(lectureBoardDTO);
	    model.addAttribute("lectureBoardDTO", lectureBoardDTO); // 이 이름이 JSP와 일치해야 합니다.
	    return "lectureboard/lectureview";
	}

	@GetMapping("/lectureboard/lectureedit.do")
	public String boardEditGet(Model model, LectureBoardDTO lectureBoardDTO) // boardDTO -> lectureBoardDTO
	{
		lectureBoardDTO = dao.viewLecture(lectureBoardDTO); // 메서드명 변경
		model.addAttribute("lectureBoardDTO", lectureBoardDTO); // 모델 속성명 boardDTO -> lectureBoardDTO
		return "lectureboard/lectureedit";
	}


	@PostMapping("/lectureboard/lectureedit.do")
	public String boardEditPost(LectureBoardDTO lectureBoardDTO) // boardDTO -> lectureBoardDTO
	{
		int result = dao.updateLecture(lectureBoardDTO); // 메서드명 변경

		return "redirect:/lectureboard/lectureview.do?lectureIdx="+lectureBoardDTO.getLectureIdx(); // 파라미터명 idx -> lectureIdx
	}

	//삭제 : @RequestParam으로 폼값 받음
	@PostMapping("/lectureboard/lecturedelete.do")
	public String boardDeletePost(@RequestParam("lectureIdx") String lectureIdx) // HttpServletRequest 대신 @RequestParam, idx -> lectureIdx
	{
		int result = dao.deleteLecture(lectureIdx); // 메서드명 변경
		return "redirect:/lectureboard/lecturelist.do";
	}
}