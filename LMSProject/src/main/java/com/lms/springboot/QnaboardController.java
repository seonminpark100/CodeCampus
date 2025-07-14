package com.lms.springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import com.lms.springboot.jdbc.QnaboardService;
import com.lms.springboot.jdbc.QnaboardDTO;
import com.lms.springboot.jdbc.ParameterDTO3;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/qnaboard") // ⭐ 클래스 레벨 매핑: 모든 메서드의 경로 앞에 "/qnaboard"가 붙습니다
public class QnaboardController {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_PROFESSOR = "PROF";

	 @Autowired
	 @Qualifier("qnaboardServiceImpl")
	 private QnaboardService qnaService;

    private String getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }
        return null;
    }

    private String getLoggedInUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                if (role.startsWith("ROLE_")) {
                    return role.substring(5);
                }
            }
        }
        return null;
    }

    // Q&A 목록 보기 
    @GetMapping("/qnaboardlist.do") 
    public String qnaList(Model model, HttpServletRequest req, ParameterDTO3 parameterDTO) {

        if (parameterDTO.getSearchField() != null && parameterDTO.getSearchField().isEmpty()) {
            parameterDTO.setSearchField(null);
        }
        if (parameterDTO.getSearchKeyword() != null && parameterDTO.getSearchKeyword().isEmpty()) {
            parameterDTO.setSearchKeyword(null);
        }
        if (parameterDTO.getLectureCode() != null && parameterDTO.getLectureCode().isEmpty()) {
            parameterDTO.setLectureCode(null);
        }

        parameterDTO.setCategory("Q");

        int totalCount = qnaService.getTotalQnaCount(parameterDTO);
        int pageSize = 10;
        int blockPage = 2;

        int pageNum = 1;
        if (req.getParameter("pageNum") != null && !req.getParameter("pageNum").isEmpty()) {
            try {
                pageNum = Integer.parseInt(req.getParameter("pageNum"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid pageNum parameter: " + req.getParameter("pageNum"));
            }
        }

        int start = (pageNum - 1) * pageSize + 1;
        int end = pageNum * pageSize;
        parameterDTO.setStart(start);
        parameterDTO.setEnd(end);

        ArrayList<QnaboardDTO> lists = qnaService.listQnaPage(parameterDTO);

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", totalCount);
        maps.put("pageSize", pageSize);
        maps.put("pageNum", pageNum);


        // req.getContextPath()는 "/프로젝트명"을 반환하므로, Spring Boot 내장 톰캣의 경우 대개 빈 문자열
        String reqUrl = "/qnaboard/qnaboardlist.do"; 
        StringBuilder paramBuilder = new StringBuilder();
        if (parameterDTO.getSearchField() != null) {
            paramBuilder.append("&searchField=").append(parameterDTO.getSearchField());
        }
        if (parameterDTO.getSearchKeyword() != null) {
            paramBuilder.append("&searchKeyword=").append(parameterDTO.getSearchKeyword());
        }
        if (parameterDTO.getLectureCode() != null) {
            paramBuilder.append("&lectureCode=").append(parameterDTO.getLectureCode());
        }

        String pagingImg;
        if (paramBuilder.length() == 0) {
            pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, reqUrl);
        } else {
             // 첫 '&' 제거 및 ? 추가 (맨 앞에 &가 붙으므로)
             // paramBuilder.substring(1) 대신 paramBuilder.insert(0, "?") 로 변경하면 더 직관적
             pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, reqUrl + "?" + paramBuilder.substring(1));
        }

        maps.put("pagingImg", pagingImg);
        maps.put("pageNum", pageNum); // 현재 페이지 번호도 maps에 추가하여 페이징 이미지 생성에 활용 (PagingUtil 내부에서 사용될 수도 있음)

        model.addAttribute("maps", maps);
        model.addAttribute("lists", lists);
        model.addAttribute("parameterDTO", parameterDTO);

        return "qnaboard/qnaboardlist";
    }

    // Q&A 게시글 작성 (GET 요청) - 작성 폼을 보여줌
    @GetMapping("/qnawrite.do") 
    public String qnaWriteGet(RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 질문을 작성할 수 있습니다.");
            return "redirect:/myLogin.do";
        }
        return "qnaboard/qnaboardwrite";
    }

    // Q&A 게시글 작성 (POST 요청) - 데이터베이스에 저장
    @PostMapping("/qnawrite.do") 
    public String qnaWritePost(QnaboardDTO qnaboardDTO, RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 질문을 작성할 수 있습니다.");
            return "redirect:/myLogin.do";
        }

        qnaboardDTO.setUserId(loggedInUserId);
        qnaboardDTO.setCategory("Q");

        int result = qnaService.insertQuestion(qnaboardDTO);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 작성되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 작성에 실패했습니다.");
        }
        return "redirect:/qnaboard/qnaboardlist.do"; // 리다이렉트 경로도 통일된 매핑 사용
    }

    // Q&A 게시글 상세 보기
    @GetMapping("/qnaboardview.do") 
    public String qnaView(Model model, @RequestParam("boardIdx") int boardIdx, RedirectAttributes redirectAttributes) {
    	 System.out.println("qnaView method called for boardIdx: " + boardIdx);
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();
        
        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }

       
        qnaService.updateVisitCount(boardIdx); // 이 부분에서 조회수가 증가합니다.

        QnaboardDTO qnaboardDTO = qnaService.viewQna(boardIdx); // 게시글 정보를 가져올 때 이미 조회수가 증가했으므로, 이 메서드 내부에서 또 증가시키지 않도록 Service Impl 확인

        if (qnaboardDTO == null || !"Q".equals(qnaboardDTO.getCategory())) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 게시글이거나 접근 권한이 없습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        boolean isAccessible = false;
        if (loggedInUserId.equals(qnaboardDTO.getUserId())) {
            isAccessible = true;
        } else if (ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole)) {
            isAccessible = true;
        }

        if (!isAccessible) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글은 작성자 또는 관리자/교수만 열람할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        model.addAttribute("qnaBoardDTO", qnaboardDTO);     
        
        return "qnaboard/qnaboardview";
    }

    // Q&A 답변 작성 폼 (GET 요청)
    @GetMapping("/qnanswer.do") 
    public String qnaAnswerGet(Model model,
                               @RequestParam("boardIdx") int boardIdx,
                               @RequestParam("bgroup") int bgroup,
                               @RequestParam("bstep") int bstep,
                               @RequestParam("bindent") int bindent,
                               RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }

        if (!(ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole))) {
            redirectAttributes.addFlashAttribute("errorMessage", "답변은 관리자 또는 교수만 작성할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        QnaboardDTO parentBoard = qnaService.viewQna(boardIdx);
        if (parentBoard == null || !"Q".equals(parentBoard.getCategory())) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 원본 게시글입니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        model.addAttribute("parentBoardIdx", boardIdx);
        model.addAttribute("parentBgroup", bgroup);
        model.addAttribute("parentBstep", bstep);
        model.addAttribute("parentBindent", bindent);
        model.addAttribute("parentBoardTitle", parentBoard.getBoardTitle());
        model.addAttribute("parentLectureCode", parentBoard.getLectureCode());

        return "qnaboard/qnaboardanswer";
    }

    // Q&A 답변 작성 (POST 요청)
    @PostMapping("/qnanswer.do") 
    public String qnaAnswerPost(QnaboardDTO qnaboardDTO,
                                @RequestParam("parentBoardIdx") int parentBoardIdx,
                                @RequestParam("parentBgroup") int parentBgroup,
                                @RequestParam("parentBstep") int parentBstep,
                                @RequestParam("parentBindent") int parentBindent,
                                RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }

        if (!(ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole))) {
            redirectAttributes.addFlashAttribute("errorMessage", "답변은 관리자 또는 교수만 작성할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        qnaboardDTO.setUserId(loggedInUserId);
        qnaboardDTO.setCategory("Q");
        qnaboardDTO.setBgroup(parentBgroup);
        qnaboardDTO.setBstep(parentBstep + 1);
        qnaboardDTO.setBindent(parentBindent + 1);

        QnaboardDTO originalParentBoard = qnaService.viewQna(parentBoardIdx);
        if (originalParentBoard != null) {
            qnaboardDTO.setLectureCode(originalParentBoard.getLectureCode());
        }

        qnaService.updateRestStep(qnaboardDTO);

        int result = qnaService.insertAnswer(qnaboardDTO);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "답변이 성공적으로 작성되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "답변 작성에 실패했습니다.");
        }

        return "redirect:/qnaboard/qnaboardview.do?boardIdx=" + parentBoardIdx;
    }

    // Q&A 게시글 수정 폼 (GET 요청)
    @GetMapping("/qnaedit.do") 
    public String qnaEditGet(Model model, @RequestParam("boardIdx") int boardIdx, RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }


        QnaboardDTO qnaboardDTO = qnaService.viewQna(boardIdx);

        if (qnaboardDTO == null || !"Q".equals(qnaboardDTO.getCategory())) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 게시글이거나 접근 권한이 없습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        boolean canEdit = false;
        if (loggedInUserId.equals(qnaboardDTO.getUserId())) {
            canEdit = true;
        } else if (ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole)) {
            canEdit = true;
        }

        if (!canEdit) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글은 작성자 또는 관리자/교수만 수정할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardview.do?boardIdx=" + boardIdx;
        }

        model.addAttribute("qnaBoardDTO", qnaboardDTO);
        return "qnaboard/qnaboardedit";
    }

    // Q&A 게시글 수정 (POST 요청)
    @PostMapping("/qnaedit.do") 
    public String qnaEditPost(QnaboardDTO qnaboardDTO, RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }

        // 원본 게시글을 가져올 때 조회수가 또 증가하지 않도록 Service 계층에 별도의 메서드(예: getQnaByIdWithoutCounting)가 필요할 수 있습니다.
        QnaboardDTO originalBoard = qnaService.viewQna(qnaboardDTO.getBoardIdx());

        if (originalBoard == null || !"Q".equals(originalBoard.getCategory())) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 게시글이거나 접근 권한이 없습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        boolean canEdit = false;
        if (loggedInUserId.equals(originalBoard.getUserId())) {
            canEdit = true;
        } else if (ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole)) {
            canEdit = true;
        }

        if (!canEdit) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글은 작성자 또는 관리자/교수만 수정할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardview.do?boardIdx=" + qnaboardDTO.getBoardIdx();
        }

        qnaboardDTO.setCategory("Q");
        qnaboardDTO.setUserId(originalBoard.getUserId());
        qnaboardDTO.setBoardPostdate(originalBoard.getBoardPostdate());
        qnaboardDTO.setVisitcount(originalBoard.getVisitcount());

        int result = qnaService.updateQna(qnaboardDTO);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 수정되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
        }
        return "redirect:/qnaboard/qnaboardview.do?boardIdx=" + qnaboardDTO.getBoardIdx();
    }

    // Q&A 게시글 삭제
    @PostMapping("/qnadelete.do") 
    public String qnaDeletePost(@RequestParam("boardIdx") int boardIdx, RedirectAttributes redirectAttributes) {
        String loggedInUserId = getLoggedInUserId();
        String loggedInUserRole = getLoggedInUserRole();

        if (loggedInUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/myLogin.do";
        }

        QnaboardDTO qnaboardDTO = qnaService.viewQna(boardIdx);
        if (qnaboardDTO == null || !"Q".equals(qnaboardDTO.getCategory())) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 게시글이거나 접근 권한이 없습니다.");
            return "redirect:/qnaboard/qnaboardlist.do";
        }

        boolean canDelete = false;
        if (loggedInUserId.equals(qnaboardDTO.getUserId())) {
            canDelete = true;
        } else if (ROLE_ADMIN.equals(loggedInUserRole) || ROLE_PROFESSOR.equals(loggedInUserRole)) {
            canDelete = true;
        }

        if (!canDelete) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글은 작성자 또는 관리자/교수만 삭제할 수 있습니다.");
            return "redirect:/qnaboard/qnaboardview.do?boardIdx=" + boardIdx;
        }

        int result = qnaService.deleteQna(boardIdx);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
        }
        return "redirect:/qnaboard/qnaboardlist.do";
    }
}