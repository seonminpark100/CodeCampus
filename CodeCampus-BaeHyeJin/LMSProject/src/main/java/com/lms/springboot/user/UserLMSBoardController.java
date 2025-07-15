package com.lms.springboot.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.lms.springboot.user.jdbc.IUserLMSBoardService;
import com.lms.springboot.user.jdbc.UserFileDTO;
import com.lms.springboot.user.jdbc.UserLMSBoardDTO;
import com.lms.springboot.user.jdbc.UserListParameterDTO;
import com.lms.springboot.utils.FileUtil;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/LMSBoard")
public class UserLMSBoardController
{
	@Autowired
	IUserLMSBoardService dao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Value("#{userProps['user.pageSize']}")
	int propPageSize;
	@Value("#{userProps['user.blockPage']}")
	int propBlockPage;
	
	@RequestMapping("lmsBoard.do")
	public String lmsBoard(Model model, @ModelAttribute("message") String message, HttpServletRequest req, UserListParameterDTO param) {		
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
		ArrayList<UserLMSBoardDTO> list = dao.selectAllBoardListPage(param);
		model.addAttribute("list", list);
		String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "lmsBoard.do?");
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("message", message);

		return "user/lmsBoard/lmsBoard";
	}
	
	@RequestMapping("lmsBoardView.do")
	public String lmsBoardView(Model model, UserLMSBoardDTO dto, HttpServletRequest req, @AuthenticationPrincipal UserDetails ud) {
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
			e.printStackTrace();
		}
		
		return "user/lmsBoard/lmsBoardView";
	}
	
	@GetMapping("lmsBoardWrite.do")
	public String lmsBoardWrite() {
		return "user/lmsBoard/lmsBoardWrite";
	}
	
	@PostMapping("lmsBoardWrite.do")
	public String lmsBoardWriteAction(Model model, UserLMSBoardDTO dto, HttpServletRequest req,
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
						ArrayList<UserFileDTO> files = FileUtil.boardFileUpload(req, dto, "files");
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
			UserLMSBoardDTO dto = dao.selectOneBoard(board_idx);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			System.out.println("LMSBoardEdit 받아오기 실패");
		}
		
		return "user/lmsBoard/lmsBoardEdit";
	}
	
	@PostMapping("lmsBoardEditAction.do")
	public String lmsBoardEditAction(Model model, UserLMSBoardDTO dto, HttpServletRequest req) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				int result = dao.updateBoard(dto);
				int result2 = 0;
				int result3 = 0;
				try
				{
					if(dao.getTotalFilesCount(dto.getBoard_idx()) > 0) {
						ArrayList<UserFileDTO> ofiles = dao.selectFiles(dto.getBoard_idx());
						result2 = dao.deleteFiles(dto.getBoard_idx());
						if(result2 < 1) {
							int error = 10 / 0;
						}
						if(FileUtil.deleteFiles(ofiles) < 1) {// 폴더에 있는 파일 삭제
							int error = 10 / 0;
						}
					}
					ArrayList<UserFileDTO> files = FileUtil.boardFileUpload(req, dto, "files");
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
	public String deleteLMSBoard(int board_idx, RedirectAttributes redirectAttributes) {
		if(dao.isReplied(dao.selectOneBoard(board_idx)) < 1) {	// 답글이 없다면
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					int result = dao.deleteBoard(board_idx);
					int result2 = 0;
					if(dao.getTotalFilesCount(board_idx) > 0) // 첨부파일이 있을때
					{
						ArrayList<UserFileDTO> ofiles = dao.selectFiles(board_idx);
						result2 = dao.deleteFiles(board_idx);
						if(result2 < 1) {
							redirectAttributes.addFlashAttribute("message", "파일 삭제 실패");
						}
						if(FileUtil.deleteFiles(ofiles) < 1) {// 폴더에 있는 파일 삭제
							redirectAttributes.addFlashAttribute("message", "파일 삭제 실패");
						}
					}
					if(result == 1) {
						System.out.println("성공");
					}else {
						redirectAttributes.addFlashAttribute("message", "게시글 삭제 실패");
					}		
				}
			});
		} else {
			redirectAttributes.addFlashAttribute("message", "답글이 달린 글은 삭제할 수 없습니다.");
		}
		
		return "redirect:lmsBoard.do";
	}
	
	@GetMapping("replyWrite.do")
	public String replyWrite(UserLMSBoardDTO dto, HttpServletRequest req, Model model) {
		dto = dao.selectOneBoard(Integer.parseInt(req.getParameter("board_idx")));
		dto.setBoard_title("[RE] [" + dto.getBoard_title() + "] ");
		dto.setBoard_content("[RE] " + (dto.getBoard_content() + "\n\n---------------------\n\n"));
		dto.setBStep(dto.getBStep() + 1);
		dto.setBIndent(dto.getBIndent() + 1);
		
		model.addAttribute("dto", dto);
		return "user/lmsBoard/replyWrite";
	}
	
	@PostMapping("replyWriteAction.do")
	public String replyWriteAction(Model model, UserLMSBoardDTO dto, HttpServletRequest req,
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
						ArrayList<UserFileDTO> files = FileUtil.boardFileUpload(req, dto, "files");
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
