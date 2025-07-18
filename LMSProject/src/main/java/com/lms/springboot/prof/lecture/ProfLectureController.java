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
	public String lectureBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) 
	{
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getLectureTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
					|| req.getParameter("pageNum").equals("")) 
					? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		PagingUtil.paging(req, profDTO, model, totalCount, pageSize, blockPage, pageNum);
		
		ArrayList<ProfDTO> lists = dao.lectureBoardListPage(profDTO);
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
	public String lectureUpload(@RequestParam String lectureCode, Model model, ProfDTO profDTO) {
//		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		profDTO = dao.lectureUploadPage(profDTO);
		model.addAttribute("profDTO", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/lectureBoard/lectureUpload";
	}
	
//	Upload a lecture wiht file processing
	@RequestMapping("/prof/lectureUploadProc.do")
	public String lectureUploadProc(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		dao.insertLectureWithoutFile(profDTO);	// Insert into a Boards table
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로: "+uploadDir);
			
			Collection<Part> parts = req.getParts();
			for(Part part: parts) {
				if(!part.getName().equals("ofile"))
					continue;
				String partHeader = part.getHeader("content-disposition");
				System.out.println("partHeader= " + partHeader);
				String[] phArr = partHeader.split("filename=");
				String originalFileName = phArr[1].trim().replace("\"", "");
				if(!originalFileName.isEmpty()) {
					part.write(uploadDir + File.separator + originalFileName);
				}
//			Save as a new name(UUID)
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			profDTO.setOfile(originalFileName);
			profDTO.setSfile(savedFileName);
			
			dao.insertLectureWithFile(profDTO);
			}
			
		} catch (Exception e)
		{
			System.out.println("Fail to uploads");
			e.printStackTrace();
		}
		
		return "redirect:lectureList.do?lectureCode="+lectureCode;
	}
	
	@RequestMapping("/prof/lectureView.do")
	public String lectureView(Model model, ProfDTO profDTO, HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		// Lecture detail without Files
		profDTO =  dao.lectureViewWithoutFile(profDTO);	
		
		// Call DAO for uploading Files
		ArrayList<ProfDTO> myFileLists = dao.lectureViewWithFile(profDTO);
		System.out.println(myFileLists);
		profDTO.setBoard_content(profDTO.getBoard_content().replace("\r\n", "<br/>"));
		
		model.addAttribute("myFileLists", myFileLists);
		model.addAttribute("result", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/lectureBoard/lectureView";
	}
	
//	Edit a lecture - Select from DB
	@RequestMapping("/prof/lectureEdit.do")
	public String lectureEdit(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		// 강의 목록 내용
		profDTO =  dao.lectureViewWithoutFile(profDTO);	
		// 파일 보기
		ArrayList<ProfDTO> myFileLists = dao.lectureViewWithFile(profDTO);
		model.addAttribute("myFileLists", myFileLists);
		model.addAttribute("profDTO", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/lectureBoard/lectureEdit";
	}
//	Edit a lecture - Processing
	@PostMapping("/prof/lectureEditProc.do")
	public String lectureEditPost(Model model, ProfDTO profDTO, HttpServletRequest req) {
		System.out.println("profDTO=" + profDTO);
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		
//		Update boards table in Oracle
		dao.lectureEditBoards(profDTO);
//		Delete files in Oracle
		dao.lectureDeleteFiles(req.getParameter("board_idx"));
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로: "+uploadDir);
			
			Collection<Part> parts = req.getParts();
			for(Part part: parts) {
				if(!part.getName().equals("ofile"))
					continue;
				String partHeader = part.getHeader("content-disposition");
				System.out.println("partHeader= " + partHeader);
				String[] phArr = partHeader.split("filename=");
				String originalFileName = phArr[1].trim().replace("\"", "");
				if(!originalFileName.isEmpty()) {
					part.write(uploadDir + File.separator + originalFileName);
				}
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			profDTO.setOfile(originalFileName);
			profDTO.setSfile(savedFileName);
			
//			Delete from files table then insert data
			dao.insertLectureWithFile(profDTO);
			}
			
		} catch (Exception e)
		{
			System.out.println("fail to upload");
			e.printStackTrace();
		}
		
		return "redirect:lectureView.do?lectureCode=" + profDTO.getLecture_code()+"&board_idx=" +profDTO.getBoard_idx();
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
