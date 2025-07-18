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

import com.lms.springboot.prof.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfAssignmentController
{
	@Autowired
	IProfAssignmentService dao;
	
	
//	Manage for the list of the submitted assignment by students (Professor view)
	@GetMapping("/prof/assignmentList.do")
	public String assignmentList(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getAssignmentTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		PagingUtil.paging(req, profDTO, model, totalCount, pageSize, blockPage, pageNum);
		
		ArrayList<ProfDTO> lists = dao.assignmentBoardListPage(profDTO);
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
	public String assignmentView(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		profDTO = dao.assignmentView(profDTO);
		profDTO.setAssignment_content(profDTO.getAssignment_content().replace("\r\n", "<br/>"));
		model.addAttribute("profDTO", profDTO);
		model.addAttribute("lectureCode", lectureCode);
		
		return "prof/assignmentBoard/assignmentView";
	}
//	Edit noticed assignment - Read
	@GetMapping("/prof/assignmentEdit.do")
	public String assignmentEditGet(Model model, ProfDTO profDTO) {
		profDTO = dao.assignmentView(profDTO);
		model.addAttribute("profDTO", profDTO);
		return "prof/assignmentBoard/assignmentEdit";
	}
//	Edit noticed assignment - Processing
	@PostMapping("/prof/assignmentEdit.do")
	public String assignmentEditPost(Model model, ProfDTO profDTO) {
		dao.assignmentEdit(profDTO);
		return "redirect:assignmentView.do?lectureCode=" + profDTO.getLecture_code()+"&assignment_idx=" +profDTO.getAssignment_idx();
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
	public String submittedAssignmentList(HttpServletRequest req, Model model, ProfDTO profDTO) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		int totalCount = dao.getSubmittedAssignmentTotalCount(profDTO);
		int pageSize = 10; 
		int blockPage = 5; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		PagingUtil.paging(req, profDTO, model, totalCount, pageSize, blockPage, pageNum);
		
		ArrayList<ProfDTO> lists = dao.submittedAssignmentBoardListPage(profDTO);
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
	public String submittedAssignmentView(Model model, ProfDTO profDTO, HttpServletRequest req) {
		String lectureCode = req.getParameter("lectureCode");
		profDTO.setLecture_code(lectureCode);
		
		profDTO = dao.submittedAssignmentView(profDTO);
		profDTO.setAssignment_content(profDTO.getAssignment_content().replace("\r\n", "<br/>"));
		String assignment_submit_idx = req.getParameter("assignment_submit_idx");
		profDTO.setAssignment_submit_idx(assignment_submit_idx);
		model.addAttribute("profDTO", profDTO);
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
	
	@GetMapping("/download.do/{fileName:.+}")
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
