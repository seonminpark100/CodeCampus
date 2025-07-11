package com.lms.springboot.prof.lecture;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	

//	강의 업로드 관리
	@RequestMapping("/prof/lectureList.do")
	public String lectureBoardList(Model model, HttpServletRequest req, ProfDTO profDTO) 
	{
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getLectureTotalCount(profDTO);
		int pageSize = 5; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
					|| req.getParameter("pageNum").equals("")) 
					? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		PagingUtil.paging(req, profDTO, model, totalCount, pageSize, blockPage, pageNum);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		ArrayList<ProfDTO> lists = dao.lectureBoardListPage(profDTO);
		model.addAttribute("lists", lists);
		model.addAttribute("lectureCode", lectureCode);
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/lectureList.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/lectureBoard/lectureBoardList";       
	}
//	강의 작성을 위한 기본값 가져오기
	@RequestMapping("/prof/lectureUpload.do")
	public String lectureUpload(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		profDTO = dao.lectureUploadPage(profDTO);
		model.addAttribute("profDTO", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/lectureBoard/lectureUpload";
	}
	
//	강의 업로드(파일 처리)
	@RequestMapping("/prof/lectureUploadProc.do")
	public String lectureUploadProc(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		int result =  dao.insertLectureWithoutFile(profDTO);	// boards 테이블에 저장
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로: "+uploadDir);
			
			Map<String, String> saveFileMaps = new HashMap<>();

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
			//서버에 저장된 파일명을 UUID를 통해 생성된 이름으로 변경한다. 
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			//DB입력 (JDBC연동)
			profDTO.setOfile(originalFileName);
			profDTO.setSfile(savedFileName);
			
			int result_files =  dao.insertLectureWithFile(profDTO); // files 테이블에 저장
			}
			
		} catch (Exception e)
		{
			System.out.println("업로드 실패");
			e.printStackTrace();
		}
		
		return "redirect:lectureList.do?lectureCode="+lectureCode;
	}
//	강의 보기
	@RequestMapping("/prof/lectureView.do")
	public String lectureView(Model model, ProfDTO profDTO, HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		// 강의 목록 내용
		profDTO =  dao.lectureViewWithoutFile(profDTO);	
		
		// 파일 보기
		ArrayList<ProfDTO> myFileLists = dao.lectureViewWithFile(profDTO);
		System.out.println(myFileLists);
		profDTO.setBoard_content(profDTO.getBoard_content().replace("\r\n", "<br/>"));
		
		model.addAttribute("myFileLists", myFileLists);
		model.addAttribute("result", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/lectureBoard/lectureView";
	}
	
//	강의 수정하기 - 내용가져오기
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
//	강의 수정하기 - action
	@PostMapping("/prof/lectureEditProc.do")
	public String lectureEditPost(Model model, ProfDTO profDTO, HttpServletRequest req) {
		System.out.println("profDTO=" + profDTO);
		
		model.addAttribute("user_id", req.getParameter("user_id"));
		model.addAttribute("lectureCode", req.getParameter("lectureCode"));
		model.addAttribute("board_title", req.getParameter("board_title"));
		model.addAttribute("board_content", req.getParameter("board_content"));
		model.addAttribute("category", req.getParameter("category"));
		
//		boards 테이블 업데이트
		int result_boards = dao.lectureEditBoards(profDTO);
//		files 테이블 삭제
		int result_files_del = dao.lectureDeleteFiles(req.getParameter("board_idx"));
		
		try
		{
			String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			System.out.println("물리적 경로: "+uploadDir);
			
			Map<String, String> saveFileMaps = new HashMap<>();

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
			//서버에 저장된 파일명을 UUID를 통해 생성된 이름으로 변경한다. 
			String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
			model.addAttribute("originalFileName", originalFileName);
			model.addAttribute("savedFileName", savedFileName);			
			
			//DB입력 (JDBC연동)
			profDTO.setOfile(originalFileName);
			profDTO.setSfile(savedFileName);
			
//			files 테이블 삭제 후 삽입 
			int result_files_insert = dao.insertLectureWithFile(profDTO);
			}
			
		} catch (Exception e)
		{
			System.out.println("업로드 실패");
			e.printStackTrace();
		}
		
		return "redirect:lectureView.do?lectureCode=" + profDTO.getLecture_code()+"&board_idx=" +profDTO.getBoard_idx();
	}
//	강의 삭제하기
	@RequestMapping("/prof/lectureDelete.do")
	public String lectureDelete(HttpServletRequest req) {
		int result_boards = dao.lectureDeleteBoards(req.getParameter("board_idx"));
		int result_files = dao.lectureDeleteFiles(req.getParameter("board_idx"));
		String lectureCode = req.getParameter("lectureCode");
		return "redirect:lectureList.do?lectureCode="+ lectureCode;
	}
	
}
