package com.lms.springboot.prof.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.prof.Paging;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAssignmentController
{
	@Autowired
	IProfAssignmentService dao;
	
	
//	Manage for the list of the submitted assignment by students (Professor view)
	@GetMapping("/prof/assignmentList.do")
	public String assignmentList(HttpServletRequest req, Model model, AssignmentDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getAssignmentTotalCount(DTO);
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
		
		ArrayList<AssignmentDTO> lists = dao.assignmentBoardListPage(DTO);
		model.addAttribute("lists", lists);

		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/assignmentList.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/assignmentBoard/assignmentList";
	}
//	Upload page for noticing assignment
	@RequestMapping("/prof/assignmentUpload.do")
	public String lectureUpload (@RequestParam String lectureCode, Model model) {
		model.addAttribute("lectureCode", lectureCode);
		return "prof/assignmentBoard/assignmentUpload";
	}
//	Upload page for noticing assignment - Processing
	@PostMapping("/prof/assignmentUpload.do")
	public String lectureUploadProc(Model model, HttpServletRequest req) {
		String title = req.getParameter("assignment_title");
		String content = req.getParameter("assignment_content");
		String deadline = req.getParameter("deadline");
		String lectureCode = req.getParameter("lectureCode");
		
		dao.assignmentlectureUploadProcProf(title, content, deadline, lectureCode);
		return "redirect:assignmentList.do?lectureCode="+lectureCode;
	}
//	Detail view for the uploaded notice assignment
	@RequestMapping("/prof/assignmentView.do")
	public String assignmentView(HttpServletRequest req, Model model, AssignmentDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		DTO = dao.assignmentView(DTO);
		DTO.setAssignment_content(DTO.getAssignment_content().replace("\r\n", "<br/>"));
		model.addAttribute("profDTO", DTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/assignmentBoard/assignmentView";
	}
//	Edit noticed assignment - Read
	@GetMapping("/prof/assignmentEdit.do")
	public String assignmentEditGet(Model model, AssignmentDTO DTO) {
		DTO = dao.assignmentView(DTO);
		model.addAttribute("profDTO", DTO);
		return "prof/assignmentBoard/assignmentEdit";
	}
//	Edit noticed assignment - Processing
	@PostMapping("/prof/assignmentEdit.do")
	public String assignmentEditPost(Model model, AssignmentDTO DTO) {
		dao.assignmentEdit(DTO);
		return "redirect:assignmentView.do?lectureCode=" + DTO.getLecture_code()+"&assignment_idx=" +DTO.getAssignment_idx();
	}
//	Delete assignment
	@PostMapping("/prof/assignmentDelete.do")
	public String assignmentDelete(HttpServletRequest req) {
		dao.assignmentDelete(req.getParameter("assignment_idx"));
		String lectureCode = req.getParameter("lectureCode");
		return "redirect:assignmentList.do?lectureCode="+ lectureCode;
	}
	
//	Uploaded Assignment Lists by Students
	@GetMapping("/prof/submittedAssignmentList.do")
	public String submittedAssignmentList(HttpServletRequest req, Model model, AssignmentDTO DTO) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getSubmittedAssignmentTotalCount(DTO);
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
		
		ArrayList<AssignmentDTO> lists = dao.submittedAssignmentBoardListPage(DTO);
		model.addAttribute("lists", lists);
		
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/submittedAssignmentList.do?lectureCode="+lectureCode+"&");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("lectureCode", lectureCode);
		return "prof/assignmentBoard/submittedAssignmentList";
	}
	
	@RequestMapping("/prof/submittedAssignmentView.do")
	public String submittedAssignmentView(Model model, AssignmentDTO DTO, HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		DTO.setLecture_code(lectureCode);
		
		DTO = dao.submittedAssignmentView(DTO);
		DTO.setAssignment_content(DTO.getAssignment_content().replace("\r\n", "<br/>"));
		String assignment_submit_idx = req.getParameter("assignment_submit_idx");
		DTO.setAssignment_submit_idx(assignment_submit_idx);
		model.addAttribute("profDTO", DTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/assignmentBoard/submittedAssignmentView";
	}
	
	@RequestMapping("/prof/submittedAssignmentGetSocreProc.do")
	public String submittedAssignmentGetSocreProc(HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		String score = req.getParameter("score");
		String assignment_comment = req.getParameter("assignment_comment");
		String assignment_submit_idx = req.getParameter("assignment_submit_idx");
		
		dao.submittedAssignmentGetSocreProc(score, lectureCode, assignment_comment, assignment_submit_idx);
		
		return "redirect:submittedAssignmentList.do?lectureCode="+lectureCode;
	}
	
	@Value("${file.upload-dir}")
	private String FILE_DIRECTORY;
	
	@GetMapping("/assignmentFileDownload.do/{fileName:.+}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) throws IOException {
        File file = new File(FILE_DIRECTORY + fileName);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // Generic binary stream

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }
}
