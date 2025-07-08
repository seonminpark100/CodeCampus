package com.lms.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.IUserService;
import com.lms.springboot.jdbc.UserDTO;
import com.lms.springboot.jdbc.UserListParameterDTO;
import com.lms.springboot.utils.FileUtil;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	IUserService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;
	
	@GetMapping("/index.do")
	public String welcome(Principal principal, Model model, HttpSession session)
	{
		String user_id = principal.getName();
		session.setAttribute("user_id", user_id);
		return "user/index";
	}
	
	@GetMapping("/myLectureList.do")
	public String myLectureList(Model model, @AuthenticationPrincipal UserDetails ud) {
		ArrayList<UserDTO> list = dao.selectAllMyLecture(ud.getUsername());
		model.addAttribute("list", list);
		return "user/myLectureList";
	}
	
	@GetMapping("/lectureList.do")
	public String lecture(Model model, UserDTO dto) {
		ArrayList<UserDTO> list = dao.selectLectureSessionList(dto.getLecture_code());
		model.addAttribute("list", list);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());

		return "user/lectureList";
	}
	
	@PostMapping("/lectureView.do")
	public String lectureView(Model model, UserDTO dto) {
		
		dto = dao.selectOneBoard(dto);
		model.addAttribute("dto", dto);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());		
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lectureView";
	}
	
	@GetMapping("/lectureBoardWrite.do")
	public String lectureBoardWrite() {
		return "user/lectureBoardWrite";
	}
	
	@GetMapping("/lectureBoardEdit.do")
	public String lectureBoardEdit() {
		return "user/lectureBoardEdit";
	}
	
	@GetMapping("/myPage.do")
	public String myPage(Model model, @AuthenticationPrincipal UserDetails ud) {
		UserDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		System.out.println(dto.getSaveFile());
		return "user/myPage";
	}
	
	@GetMapping("/infoEdit.do")
	public String infoEdit(Model model, @AuthenticationPrincipal UserDetails ud) {
		UserDTO dto = dao.selectOneUser(ud.getUsername());
		model.addAttribute("dto", dto);
		return "user/infoEdit";
	}
	
	@PostMapping("/infoEdit.do")
	public String infoEditAction(Model model, UserDTO dto, HttpServletRequest req)
	{
		try
		{
			String passwd =	PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dto.getUser_pw());
			passwd = passwd.substring(8);
			dto.setUser_pw(passwd);
			
			dto = FileUtil.singleFileUpload(req, dto, "profileImg");
			
			int result = dao.updateUser(dto);
			if(result == 1)	System.out.println("성공");
			else System.out.println("실패");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "redirect:myPage.do";
	}
	
	@GetMapping("/registLecture.do")
	public String registLecture(Model model, @ModelAttribute("message") String message, HttpServletRequest req, UserListParameterDTO param)
	{
		int totalCount = dao.getTotalLectureCount(param);
		
		int pageSize = this.propPageSize;
		int blockPage = this.propBlockPage;
		
		int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		param.setStart(start);
		param.setEnd(end);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		ArrayList<UserDTO> list = dao.selectAllLecturePage(param);
		model.addAttribute("list", list);
		
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "registLecture.do?");
		model.addAttribute("pagingImg", pagingImg);
		System.out.println(message);
		model.addAttribute("message", message);
		
		return "user/registLecture";
	}
	
	@PostMapping("/registLecture.do")
	public String registLectureAction(RedirectAttributes redirectAttributes, HttpServletResponse resp, UserDTO dto,
			@AuthenticationPrincipal UserDetails ud) throws IOException
	{
		dto.setUser_id(ud.getUsername());
		if(dao.duplicationEnrollCheck(dto) == 0)	// 이미 수강중인 강의가 아닐때
		{
			try
			{
				transactionTemplate.execute(new TransactionCallbackWithoutResult()
				{
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status)
					{
						int result = dao.insertEnroll(dto);
						if(result == 1) redirectAttributes.addFlashAttribute("message", "수강신청 성공!!");
						else {
							int error = 10 / 0;
						}
					}
				});
			} catch (Exception e)
			{
				redirectAttributes.addFlashAttribute("message", "수강신청 실패...");
			}
		} else
		{
			redirectAttributes.addFlashAttribute("message", "이미 수강중인 강의입니다.");
		}
		return "redirect:registLecture.do";
	}
	
	@RequestMapping("/assignmentList.do")
	public String assignmentList(Model model, HttpServletRequest req, UserListParameterDTO param, 
			@AuthenticationPrincipal UserDetails ud)
	{
		param.setUser_id(ud.getUsername());
		int pageSize = this.propPageSize;
		int blockPage = this.propBlockPage;
		
		int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		param.setStart(start);
		param.setEnd(end);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		ArrayList<UserDTO> list = dao.selectAllMyAssignmentList(param);
		int totalCount = list.size();
		
		maps.put("totalCount", totalCount);
		model.addAttribute("list", list);
		
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "assignmentList.do?");
		model.addAttribute("pagingImg", pagingImg);
		return "user/assignmentList";
	}
	
	@RequestMapping("assignmentSubmitWrite.do")
	public String assignmentWrite(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud)
	{
		dto.setUser_id(ud.getUsername());
		int result = dao.submitCheck(dto);
		if(result == 0)
		{
			dto = dao.selectOneAssignment(dto.getAssignment_idx());
			return "user/assignmentSubmitWrite";	
		}
		model.addAttribute("dto", dto);
		return "assignmentSubmitView.do";
	}
	
	@PostMapping("assignmentWriteAction.do")
	public String assignmentWriteAction(Model model, UserDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud)
	{
		dto.setAssignment_idx(Integer.parseInt(req.getParameter("idx")));
		dto.setUser_id(ud.getUsername());
		int result = dao.insertAssignmentSubmit(dto);
		
		if(result == 1)	System.out.println("성공");
		else System.out.println("실패");
		
		return "redirect:assignmentSubmitView.do";
	}
	
	@RequestMapping("assignmentSubmitView.do")
	public String assignmentSubmitView(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud)
	{
		dto.setUser_id(ud.getUsername());
		dto = dao.selectOneAssignmentSubmit(dto.getUser_id());
		System.out.println(dto.getAssignment_content_s());
		model.addAttribute("dto", dto);
		
		return "user/assignmentSubmitView";
	}
	
	@GetMapping("lmsBoard.do")
	public String lmsBoard(Model model, HttpServletRequest req, UserListParameterDTO param)
	{		
		param.setCategory("C");
		int totalCount = dao.getTotalBoardCount(param);
		
		int pageSize = this.propPageSize;
		int blockPage = this.propBlockPage;
		
		int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals(""))
				? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		param.setStart(start);
		param.setEnd(end);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		ArrayList<UserDTO> list = dao.selectAllBoardListPage(param);
//		ArrayList<UserDTO> list = dao.selectAllLMSBoardList();
		model.addAttribute("list", list);
		
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "lmsBoard.do?");
		model.addAttribute("pagingImg", pagingImg);

		return "user/lmsBoard";
	}
	
	@RequestMapping("lmsBoardView.do")
	public String lmsBoardView(Model model, UserDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud)
	{
		dto.setCategory("C");
		dto.setBoard_idx(Integer.parseInt(req.getParameter("board_idx")));
		
		try
		{
			dto = dao.selectOneBoard(dto);
			model.addAttribute("dto", dto);
			if(dto.getUser_id().equals(ud.getUsername())) 
				model.addAttribute("isWriter", true);
			else 
				model.addAttribute("isWriter", false);
			String files = FileUtil.getFiles(dao.selectFiles(dto.getBoard_idx()));
			if(!(files.equals(""))) {	//첨부파일이 있을때
				model.addAttribute("files", files);
			}
		} catch (Exception e)
		{
			System.out.println("LMSView 받아오기 실패");
		}
		
		return "user/lmsBoardView";
	}
	
	@GetMapping("lmsBoardWrite.do")
	public String lmsBoardWrite() {
		return "user/lmsBoardWrite";
	}
	
	@PostMapping("lmsBoardWrite.do")
	public String lmsBoardWriteAction(Model model, UserDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setCategory("C");
		dto.setUser_id(ud.getUsername());

		transactionTemplate.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				int result = dao.insertBoard(dto);
				if (result == 1)
				{
					dto.setBoard_idx(dto.getBoard_idx());
					try
					{
						dao.insertFiles(FileUtil.boardFileUpload(req, dto, "files"));
					} catch (IOException | ServletException e)
					{
						e.printStackTrace();
					}
					System.out.println("성공");
				}
				else {
					int error = 10 / 0;
				}				
			}
		});
				
		return "redirect:lmsBoard.do";
	}
	
	@PostMapping("lmsBoardEdit.do")
	public String lmsBoardEdit(Model model, HttpServletRequest req, UserDTO dto) {
		dto.setBoard_idx(Integer.parseInt(req.getParameter("board_idx")));
		dto.setCategory("C");
		dto = dao.selectOneBoard(dto);
		
		model.addAttribute("dto", dto);
		
		return "user/lmsBoardEdit";
	}
	
	@PostMapping("lmsBoardEditAction.do")
	public String lmsBoardEditAction(Model model, UserDTO dto) {
		
		model.addAttribute("dto", dto);
		return "redirect:lmsBoardView.do?board_idx=" + dto.getBoard_idx();
	}
	
	@RequestMapping("download.do")
	public void download(UserDTO dto, HttpServletRequest req, HttpServletResponse resp, String redirect) {
		String fileName = req.getParameter("fileName");
		dto = dao.selectOneFile(fileName);
		FileUtil.downloadFile(dto.getSfile(), dto.getOfile(), resp);
	}
}
