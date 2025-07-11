package com.lms.springboot;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.IUserService;
import com.lms.springboot.jdbc.UserDTO;
import com.lms.springboot.jdbc.UserListParameterDTO;
import com.lms.springboot.utils.FileUtil;
import com.lms.springboot.utils.InterfaceUtil;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{

    private final SecurityFilterChain filterChain;
	@Autowired
	IUserService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;

    UserController(SecurityFilterChain filterChain) {
        this.filterChain = filterChain;
    }
	
	@GetMapping("/index.do")
	public String welcome(Principal principal, Model model, HttpSession session) {
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
	public String lecture(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud, HttpServletResponse resp) {
		// 수강중인거 체크하기
//		InterfaceUtil.alertLocation(resp, "잘못된 접근입니다.", "index.do");
		
		ArrayList<UserDTO> list = dao.selectLectureSessionList(dto.getLecture_code());
		model.addAttribute("list", list);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lectureList";
	}
	
	@RequestMapping("/lectureView.do")
	public String lectureView(Model model, UserDTO dto) {
		dao.increaseVisitCount(dto.getBoard_idx());
		dto.setCategory("L");
		dto = dao.selectOneBoard(dto.getBoard_idx());
		dto.setBoard_content(dto.getBoard_content().replaceAll("\r\n", "<br/>"));
		ArrayList<UserDTO> files = dao.selectFiles(dto.getBoard_idx());
		ArrayList<UserDTO> videoList = new ArrayList<>();
		ArrayList<UserDTO> fileList = new ArrayList<>();
		List<String> videoExt = Arrays.asList("mp4", "mov", "wmv", "avi", "mkv");
		
		for(UserDTO file : files) {
			
			if(videoExt.contains(file.getSfile().substring(file.getSfile().lastIndexOf(".") + 1).toLowerCase())) { // 파일이 비디오라면
				videoList.add(file);
			} else { // 파일이 비디오가 아니라면
				fileList.add(file);
			}
		}
		model.addAttribute("video", FileUtil.getVideoFile(videoList));
		model.addAttribute("file", FileUtil.getFiles(fileList)); 
		
		model.addAttribute("dto", dto);
		UserDTO lecture = dao.selectOneLecture(dto.getLecture_code());		
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lectureView";
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
	public String infoEditAction(Model model, UserDTO dto, HttpServletRequest req) {
		try {
			String passwd =	PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dto.getUser_pw());
			passwd = passwd.substring(8);
			dto.setUser_pw(passwd);
			
			dto = FileUtil.singleFileUpload(req, dto, "profileImg", "User");
			
			int result = dao.updateUser(dto);
			if(result == 1)	System.out.println("성공");
			else System.out.println("실패");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:myPage.do";
	}
	
	@GetMapping("/registLecture.do")
	public String registLecture(Model model, @ModelAttribute("message") String message, HttpServletRequest req, UserListParameterDTO param) {
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
			@AuthenticationPrincipal UserDetails ud) throws IOException {
		dto.setUser_id(ud.getUsername());
		if(dao.duplicationEnrollCheck(dto) == 0) {	// 이미 수강중인 강의가 아닐때
			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						int result = dao.insertEnroll(dto);
						if(result == 1) redirectAttributes.addFlashAttribute("message", "수강신청 성공!!");
						else {
							int error = 10 / 0;
						}
					}
				});
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "수강신청 실패...");
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "이미 수강중인 강의입니다.");
		}
		return "redirect:registLecture.do";
	}
	
	@RequestMapping("/assignmentList.do")
	public String assignmentList(Model model, HttpServletRequest req, UserListParameterDTO param, 
			@AuthenticationPrincipal UserDetails ud) {
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
	public String assignmentWrite(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		int result = dao.submitCheck(dto);
		if(result == 0) {
			dto = dao.selectOneAssignment(dto.getAssignment_idx());
			dto.setAssignment_content(dto.getAssignment_content().replaceAll("\r\n", "<br/>"));
			model.addAttribute("dto", dto);
			return "user/assignmentSubmitWrite";	
		}
		return "redirect:assignmentSubmitView.do";
	}
	
	@PostMapping("assignmentWriteAction.do")
	public String assignmentWriteAction(Model model, UserDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setAssignment_idx(Integer.parseInt(req.getParameter("idx")));
		dto.setUser_id(ud.getUsername());

		try {
			dto = FileUtil.singleFileUpload(req, dto, "assignmentFile", "Assignment");
			int result = dao.insertAssignmentSubmit(dto);
			
			if (result == 1)
				System.out.println("성공");
			else
				System.out.println("실패");
		} catch (IOException | ServletException e){
			e.printStackTrace();
		}
//		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//			@Override
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				
//		});
		
		return "redirect:assignmentSubmitView.do";
	}
	
	@RequestMapping("assignmentSubmitView.do")
	public String assignmentSubmitView(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		dto = dao.selectOneAssignmentSubmit(dto.getUser_id());
		dto.setAssignment_content(dto.getAssignment_content().replaceAll("\r\n", "<br/>"));
		dto.setAssignment_content_s(dto.getAssignment_content_s().replaceAll("\r\n", "<br/>"));
		model.addAttribute("dto", dto);
		model.addAttribute("file", FileUtil.getAssignFile(dto.getAssignment_ofile(), dto.getAssignment_sfile()));
		return "user/assignmentSubmitView";
	}
	
	@RequestMapping("assignmentSubmitEdit.do")
	public String assignmentEdit(Model model, UserDTO dto, @AuthenticationPrincipal UserDetails ud) {
		dto = dao.selectOneAssignmentSubmit(ud.getUsername());
		
		model.addAttribute("dto", dto);
		return "user/assignmentSubmitEdit";
	}
	
	@RequestMapping("assignmentSubmitEditAction.do")
	public String assignmentSubmitEditAction(UserDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud) {
		dto.setUser_id(ud.getUsername());
		try {
			if(dto.getAssignment_sfile().equals(""))
			{
				FileUtil.deleteAssignmentFile(dto);
			}
			dto = FileUtil.singleFileUpload(req, dto, "assignmentFile", "Assignment");
			dto.setUser_id(ud.getUsername());
			System.out.println(dto);
			int result = dao.updateAssignmentSubmit(dto);
			if(result != 1) {
				int error = 10 / 0;
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		
		return "redirect:assignmentSubmitView.do";
	}
	
	@RequestMapping("lmsBoard.do")
	public String lmsBoard(Model model, HttpServletRequest req, UserListParameterDTO param) {		
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
		model.addAttribute("list", list);
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "lmsBoard.do?");
		model.addAttribute("pagingImg", pagingImg);

		return "user/lmsBoard";
	}
	
	@RequestMapping("lmsBoardView.do")
	public String lmsBoardView(Model model, UserDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud) {
		dto.setBoard_idx(Integer.parseInt(req.getParameter("board_idx")));
		
		try {
			dao.increaseVisitCount(dto.getBoard_idx());
			dto = dao.selectOneBoard(dto.getBoard_idx());
			dto.setBoard_content(dto.getBoard_content().replaceAll("\r\n", "<br/>"));
			model.addAttribute("dto", dto);
			if(dto.getUser_id().equals(ud.getUsername())) 
				model.addAttribute("isWriter", true);
			else 
				model.addAttribute("isWriter", false);
			String files = FileUtil.getFiles(dao.selectFiles(dto.getBoard_idx()));
			if(!(files.equals(""))) {	//첨부파일이 있을때
				model.addAttribute("files", files);
			}
		} catch (Exception e) {
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

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				int result = dao.insertBoard(dto);
				int result2 = 0;
				if (result == 1) {
					dto.setBoard_idx(dto.getBoard_idx());
					try {
						ArrayList<UserDTO> files = FileUtil.boardFileUpload(req, dto, "files");
						if(files.size() > 0)
						{
							result2 = dao.insertFiles(files);
							if(result2 >= 1) {
								System.out.println("파일 업로드 성공");
							} else {
								int error = 10 / 0;
							}
						}
						
					} catch (IOException | ServletException e) {
						e.printStackTrace();
					}
					System.out.println("성공");
				}
				else {
					int error = 10 / 0;
				}				
			}
		});
				
		return "redirect:lmsBoardView.do?board_idx=" + dto.getBoard_idx();
	}
	
	@RequestMapping("lmsBoardEdit.do")
	public String lmsBoardEdit(Model model, HttpServletRequest req, int board_idx) {
		try {
			UserDTO dto = dao.selectOneBoard(board_idx);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			System.out.println("LMSBoardEdit 받아오기 실패");
		}
		
		return "user/lmsBoardEdit";
	}
	
	@PostMapping("lmsBoardEditAction.do")
	public String lmsBoardEditAction(Model model, UserDTO dto, HttpServletRequest req) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				int result = dao.updateBoard(dto);
				int result2 = 0;
				int result3 = 0;
				try
				{
					if(dao.getTotalFilesCount(dto.getBoard_idx()) > 0) {
						ArrayList<UserDTO> ofiles = dao.selectFiles(dto.getBoard_idx());
						result2 = dao.deleteFiles(dto.getBoard_idx());
						if(result2 < 1) {
							int error = 10 / 0;
						}
						if(FileUtil.deleteFiles(ofiles) < 1) {// 폴더에 있는 파일 삭제
							int error = 10 / 0;
						}
					}
					ArrayList<UserDTO> files = FileUtil.boardFileUpload(req, dto, "files");
					if(files.size() > 0) {
						result3 = dao.insertFiles(files);
						if(result3 < 1) {
							int error = 10 / 0;
						}
					}
				} catch (IOException | ServletException e)
				{
					e.printStackTrace();
				}
				if (result == 1) {
					System.out.println("성공");
				}else {
					int error = 10 / 0;
				}		
			}
		});
		
		return "redirect:lmsBoardView.do?board_idx=" + dto.getBoard_idx();
	}
	
	@RequestMapping("deleteBoard.do")
	public String deleteLMSBoard(int board_idx) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				int result = dao.deleteBoard(board_idx);
				int result2 = 0;
				if(dao.getTotalFilesCount(board_idx) > 0) // 첨부파일이 있을때
				{
					ArrayList<UserDTO> ofiles = dao.selectFiles(board_idx);
					result2 = dao.deleteFiles(board_idx);
					if(result2 < 1) {
						int error = 10 / 0;
					}
					if(FileUtil.deleteFiles(ofiles) < 1) {// 폴더에 있는 파일 삭제
						int error = 10 / 0;
					}
				}
				if(result == 1) {
					System.out.println("성공");
				}else {
					int error = 10 / 0;
				}		
			}
		});
		return "redirect:lmsBoard.do";
	}
	
	@RequestMapping("download.do")
	public void download(UserDTO dto, HttpServletRequest req, HttpServletResponse resp) {
		String fileName = req.getParameter("fileName");
		
		dto = dao.selectOneFile(fileName);
		FileUtil.downloadFile(dto.getSfile(), dto.getOfile(), resp);
	}	
	
	@RequestMapping("assignDownload.do")
	public void assignDownload(UserDTO dto, HttpServletRequest req, HttpServletResponse resp,
			@AuthenticationPrincipal UserDetails ud) {
		String fileName = req.getParameter("fileName");
		dto = dao.selectOneAssignmentSubmit(ud.getUsername());
		FileUtil.downloadFile(dto.getAssignment_sfile(), dto.getAssignment_ofile(), resp);
	}
	
	@GetMapping("replyWrite.do")
	public String replyWrite(UserDTO dto, HttpServletRequest req, Model model) {
		dto = dao.selectOneBoard(Integer.parseInt(req.getParameter("board_idx")));
		dto.setBoard_title("[RE] [" + dto.getBoard_title() + "] ");
		dto.setBoard_content("[RE] " + (dto.getBoard_content() + "\n\n---------------------\n\n"));
		dto.setBStep(dto.getBStep() + 1);
		dto.setBIndent(dto.getBIndent() + 1);
		
		model.addAttribute("dto", dto);
		return "user/replyWrite";
	}
	
	@PostMapping("replyWriteAction.do")
	public String ReplyWriteAction(Model model, UserDTO dto, HttpServletRequest req,
			@AuthenticationPrincipal UserDetails ud) {
		dto.setCategory("C");
		dto.setUser_id(ud.getUsername());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				dao.updateBStep(dto);
				int result = dao.insertReply(dto);
				int result2 = 0;
				if (result == 1) {
					dto.setBoard_idx(dto.getBoard_idx());
					try {
						ArrayList<UserDTO> files = FileUtil.boardFileUpload(req, dto, "files");
						if(files.size() > 0)
						{
							result2 = dao.insertFiles(files);
							if(result2 >= 1) {
								System.out.println("파일 업로드 성공");
							} else {
								int error = 10 / 0;
							}
						}
						
					} catch (IOException | ServletException e) {
						e.printStackTrace();
					}
					System.out.println("성공");
				}
				else {
					int error = 10 / 0;
				}				
			}
		});
				
		return "redirect:lmsBoardView.do?board_idx=" + dto.getBoard_idx();
	}
}
