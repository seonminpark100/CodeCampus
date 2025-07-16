package com.lms.springboot.prof.lecture;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.prof.Paging;
import com.lms.springboot.prof.ProfDTO;
import com.lms.springboot.utils.MyFunctions;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@Controller
public class ProfLectureController
{
	@Autowired
	IProfLectureService dao;
	

//	Lecture Lists
	@RequestMapping("/prof/lectureList.do")
	public String lectureBoardList(Model model, HttpServletRequest req, LectureDTO DTO) 
	{
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getLectureTotalCount(DTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
					|| req.getParameter("pageNum").equals("")) 
					? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		Paging.paging(req, model, totalCount, pageSize, blockPage, pageNum);
		
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		
		DTO.setStart(start);
		DTO.setEnd(end);
		
		ArrayList<LectureDTO> lists = dao.lectureBoardListPage(DTO);
		model.addAttribute("lists", lists);
		model.addAttribute("lectureCode", lectureCode);
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/lectureList.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/lectureBoard/lectureBoardList";       
	}

	@RequestMapping("/prof/lectureUpload.do")
	public String lectureUpload(@RequestParam String lectureCode, Model model, LectureDTO DTO) {
//		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		DTO = dao.lectureUploadPage(DTO);
		model.addAttribute("profDTO", DTO);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/lectureBoard/lectureUpload";
	}
	
//	Upload a lecture wiht file processing
	@RequestMapping("/prof/lectureUploadProc.do")
	public String lectureUploadProc(HttpServletRequest req, Model model,  LectureDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		dao.insertLectureWithoutFile(DTO);	// Insert into a Boards table
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			
			Collection<Part> parts = req.getParts();
			for(Part part: parts) {
				if(!part.getName().equals("ofile"))
					continue;
				String partHeader = part.getHeader("content-disposition");
				String[] phArr = partHeader.split("filename=");
				String originalFileName = phArr[1].trim().replace("\"", "");
				if(!originalFileName.isEmpty()) {
					part.write(uploadDir + File.separator + originalFileName);
				}
//			Save as a new name(UUID)
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			DTO.setOfile(originalFileName);
			DTO.setSfile(savedFileName);
			
			dao.insertLectureWithFile(DTO);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "redirect:lectureList.do?lectureCode="+lectureCode;
	}
	
	@RequestMapping("/prof/lectureView.do")
	public String lectureView(Model model, LectureDTO DTO, HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		// Lecture detail without Files
		DTO =  dao.lectureViewWithoutFile(DTO);	
		
		// Call DAO for uploading Files
		ArrayList<LectureDTO> myFileLists = dao.lectureViewWithFile(DTO);
		DTO.setBoard_content(DTO.getBoard_content().replace("\r\n", "<br/>"));
		
		model.addAttribute("myFileLists", myFileLists);
		model.addAttribute("result", DTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/lectureBoard/lectureView";
	}
	
//	Edit a lecture - Select from DB
	@RequestMapping("/prof/lectureEdit.do")
	public String lectureEdit(HttpServletRequest req, Model model, LectureDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		// 강의 목록 내용
		DTO =  dao.lectureViewWithoutFile(DTO);	
		// 파일 보기
		ArrayList<LectureDTO> myFileLists = dao.lectureViewWithFile(DTO);
		model.addAttribute("myFileLists", myFileLists);
		model.addAttribute("profDTO", DTO);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/lectureBoard/lectureEdit";
	}
//	Edit a lecture - Processing
	@PostMapping("/prof/lectureEditProc.do")
	public String lectureEditPost(Model model, LectureDTO DTO, HttpServletRequest req) {
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		
//		Update boards table in Oracle
		dao.lectureEditBoards(DTO);
//		Delete files in Oracle
		dao.lectureDeleteFiles(req.getParameter("board_idx"));
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			
			Collection<Part> parts = req.getParts();
			for(Part part: parts) {
				if(!part.getName().equals("ofile"))
					continue;
				String partHeader = part.getHeader("content-disposition");
				String[] phArr = partHeader.split("filename=");
				String originalFileName = phArr[1].trim().replace("\"", "");
				if(!originalFileName.isEmpty()) {
					part.write(uploadDir + File.separator + originalFileName);
				}
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			DTO.setOfile(originalFileName);
			DTO.setSfile(savedFileName);
			
//			Delete from files table then insert data
			dao.insertLectureWithFile(DTO);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "redirect:lectureView.do?lectureCode=" + DTO.getLecture_code()+"&board_idx=" +DTO.getBoard_idx();
	}
//	Delete Lecture
	@RequestMapping("/prof/lectureDelete.do")
	public String lectureDelete(HttpServletRequest req) {
		dao.lectureDeleteBoards(req.getParameter("board_idx"));
		dao.lectureDeleteFiles(req.getParameter("board_idx"));
		String lectureCode = req.getParameter("lectureCode");
		return "redirect:lectureList.do?lectureCode="+ lectureCode;
	}
	
}
