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

import com.lms.springboot.jdbc.INoticeService;
import com.lms.springboot.jdbc.NoticeBoardDTO;
import com.lms.springboot.jdbc.ParameterDTO2;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class NoticeBoardController {

    @Autowired
    INoticeService dao;

    // 공지사항 목록
    @GetMapping("/noticeboard/noticelist.do")
    public String noticeboardList(Model model, HttpServletRequest req, ParameterDTO2 parameterDTO) {
        int totalCount = dao.getTotalNoticeCount(parameterDTO);
        int pageSize = 10;
        int blockPage = 2;

        int pageNum = (req.getParameter("pageNum") == null || req.getParameter("pageNum").equals("")) ? 1 : Integer.parseInt(req.getParameter("pageNum"));

        int start = (pageNum - 1) * pageSize + 1;
        int end = pageNum * pageSize;
        parameterDTO.setStart(start);
        parameterDTO.setEnd(end);

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", totalCount);
        maps.put("pageSize", pageSize);
        maps.put("pageNum", pageNum);
        model.addAttribute("maps", maps);

        ArrayList<NoticeBoardDTO> lists = dao.listNoticePage(parameterDTO);
        model.addAttribute("lists", lists);

        String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "/noticeboard/noticelist.do?");
        model.addAttribute("pagingImg", pagingImg);

        return "noticeboard/noticelist";
    }

    // 공지사항 작성 폼
    @GetMapping("/noticeboard/noticewrite.do")
    public String boardWriteGet(Model model) { // HttpServletRequest, HttpSession 관련 파라미터 제거
        return "noticeboard/noticewrite";
    }

    // 공지사항 작성 처리
    @PostMapping("/noticeboard/noticewrite.do")
    public String boardWritePost(NoticeBoardDTO noticeBoardDTO) { // HttpServletRequest 파라미터 제거

    	 if (noticeBoardDTO.getBoard_Content() == null) {
             noticeBoardDTO.setBoard_Content("");
         }
    	
        noticeBoardDTO.setUser_Id("공지담당자"); // ★★★ 예시: 'guest'로 고정 (DB에 해당 ID가 없어도 가능하도록)
                                           // 실제 사용 시 '관리자', '공지담당' 등으로 지정 가능
                                     // DB의 USER_ID 컬럼이 NOT NULL이면 이 부분 필수!

        noticeBoardDTO.setCategory("N"); // 공지사항 카테고리를 'N'으로 고정
        System.out.println("등록 요청 DTO: " + noticeBoardDTO); // DTO 값 확인 (userId, boardTitle, boardContent)
        int result = dao.insertNotice(noticeBoardDTO);
        System.out.println("등록 결과 (0=실패, 1=성공): " + result);
        return "redirect:/noticeboard/noticelist.do";
    }

    // 공지사항 상세 보기
    @RequestMapping("/noticeboard/noticeview.do")
    public String boardView(Model model, @RequestParam("board_Idx") int board_Idx) {
        dao.updateVisitCount(board_Idx);
        NoticeBoardDTO noticeBoardDTO = dao.viewNotice(board_Idx);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);
        return "noticeboard/noticeview";
    }

    // 공지사항 수정 폼
    @GetMapping("/noticeboard/noticeedit.do")
    public String boardEditGet(Model model, @RequestParam("board_Idx") int board_Idx) {
        NoticeBoardDTO noticeBoardDTO = dao.viewNotice(board_Idx);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);
        return "noticeboard/noticeedit";
    }

    // 공지사항 수정 처리
    @PostMapping("/noticeboard/noticeedit.do")
    public String boardEditPost(NoticeBoardDTO noticeBoardDTO) {
        int result = dao.updateNotice(noticeBoardDTO);
        return "redirect:/noticeboard/noticeview.do?board_Idx=" + noticeBoardDTO.getBoard_Idx();
    }

    // 공지사항 삭제 처리
    @PostMapping("/noticeboard/noticedelete.do")
    public String boardDeletePost(@RequestParam("board_Idx") int board_Idx) {
        int result = dao.deleteNotice(board_Idx);
        return "redirect:/noticeboard/noticelist.do";
    }

    // 메인 페이지 등에 표시할 최신 공지사항 API
    @GetMapping("/noticeboard/api/latestNotices")
    @ResponseBody
    public ArrayList<NoticeBoardDTO> getLatestNotices(ParameterDTO2 parameterDTO) {
        try {
            parameterDTO.setStart(1);
            parameterDTO.setEnd(5);
            ArrayList<NoticeBoardDTO> notices = dao.listNoticePage(parameterDTO);
            return notices;
        } catch (Exception e) {
            System.err.println("!!! 공지사항 로드 중 오류 발생 (DB/DAO): " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}