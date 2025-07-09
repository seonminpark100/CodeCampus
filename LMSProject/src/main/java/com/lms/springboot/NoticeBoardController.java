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
import jakarta.servlet.http.HttpSession; // 추가되어야 할 경우 (로그인 처리 시)

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
    public String boardWriteGet(Model model) {
        // 이전에 세션에서 userId 가져오는 부분은 주석 처리했으므로, 다시 필요하다면 추가
        // HttpSession session = (HttpSession) request.getSession();
        // String loggedInUserId = (String) session.getAttribute("userId");
        // if (loggedInUserId != null) {
        //     model.addAttribute("loggedInUserId", loggedInUserId);
        // } else {
        //     return "redirect:/login.do";
        // }
        return "noticeboard/noticewrite";
    }

    @PostMapping("/noticeboard/noticewrite.do")
    public String boardWritePost(NoticeBoardDTO noticeBoardDTO) {
        // 이전에 세션에서 userId 가져오는 부분은 주석 처리했으므로, 다시 필요하다면 추가
        // HttpSession session = (HttpSession) request.getSession();
        // String loggedInUserId = (String) session.getAttribute("userId");
        // if (loggedInUserId != null) {
        //     noticeBoardDTO.setUserId(loggedInUserId);
        // } else {
        //     return "redirect:/login.do";
        // }

        System.out.println("등록 요청 DTO: " + noticeBoardDTO);
        int result = dao.insertNotice(noticeBoardDTO);
        System.out.println("등록 결과 (0=실패, 1=성공): " + result);
        return "redirect:/noticeboard/noticelist.do";
    }

    // 공지사항 상세 보기
    @RequestMapping("/noticeboard/noticeview.do")
    public String boardView(Model model, @RequestParam("boardIdx") int boardIdx) {
        dao.updateVisitCount(boardIdx);
        NoticeBoardDTO noticeBoardDTO = dao.viewNotice(boardIdx);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);
        return "noticeboard/noticeview";
    }

    // 공지사항 수정 폼
    @GetMapping("/noticeboard/noticeedit.do")
    public String boardEditGet(Model model, @RequestParam("boardIdx") int boardIdx) {
        NoticeBoardDTO noticeBoardDTO = dao.viewNotice(boardIdx);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);
        return "noticeboard/noticeedit";
    }

    // 공지사항 수정 처리
    @PostMapping("/noticeboard/noticeedit.do")
    public String boardEditPost(NoticeBoardDTO noticeBoardDTO) {
        int result = dao.updateNotice(noticeBoardDTO);
        return "redirect:/noticeboard/noticeview.do?boardIdx=" + noticeBoardDTO.getBoardIdx();
    }

    // 공지사항 삭제 처리
    @PostMapping("/noticeboard/noticedelete.do")
    public String boardDeletePost(@RequestParam("boardIdx") int boardIdx) {
        int result = dao.deleteNotice(boardIdx);
        return "redirect:/noticeboard/noticelist.do";
    }

    @GetMapping("/noticeboard/api/latestNotices")
    @ResponseBody // 이 어노테이션이 정말 중요합니다! 없으면 HTML 뷰를 찾으려 합니다.
    public ArrayList<NoticeBoardDTO> getLatestNotices(ParameterDTO2 parameterDTO) {

        try {
            parameterDTO.setStart(1);
            parameterDTO.setEnd(5);
            ArrayList<NoticeBoardDTO> notices = dao.listNoticePage(parameterDTO);
            return notices; // JSON으로 변환되어야 함
        } catch (Exception e) {
            // 데이터베이스 조회 중 오류가 발생하면 이 블록으로 들어옵니다.
            System.err.println("!!! 공지사항 로드 중 오류 발생 (DB/DAO): " + e.getMessage());
            e.printStackTrace(); // 스택 트레이스 확인
            return new ArrayList<>();
        }
    }
}