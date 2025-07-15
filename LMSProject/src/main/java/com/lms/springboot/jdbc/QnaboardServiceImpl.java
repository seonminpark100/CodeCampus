package com.lms.springboot.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service 
public class QnaboardServiceImpl implements QnaboardService {

    @Autowired // <-- QnaboardDAO 인터페이스를 주입받습니다.
    private QnaboardDAO qnaboardDAO;

    // --- 공지사항 관련 메소드 구현 ---
    @Override
    public int getTotalNoticeCount(ParameterDTO3 parameterDTO) {
        return qnaboardDAO.getTotalNoticeCount(parameterDTO);
    }

    @Override
    public ArrayList<QnaboardDTO> listNoticePage(ParameterDTO3 parameterDTO) {
        return qnaboardDAO.listNoticePage(parameterDTO);
    }

    @Override
    public int insertNotice(QnaboardDTO dto) {
        return qnaboardDAO.insertNotice(dto);
    }

    @Override
    public QnaboardDTO viewNotice(int board_Idx) {
        // ⭐️ 수정: viewNotice 내부에서 조회수 증가 로직을 제거합니다.
        // 조회수 증가 로직은 Controller에서 명시적으로 호출합니다.
        return qnaboardDAO.viewNotice(board_Idx);
    }

    @Override
    public int updateNotice(QnaboardDTO dto) {
        return qnaboardDAO.updateNotice(dto);
    }

    @Override
    public int deleteNotice(int board_Idx) {
        return qnaboardDAO.deleteNotice(board_Idx);
    }

    @Override
    public void updateVisitCount(int board_Idx) {
        qnaboardDAO.updateVisitCount(board_Idx);
    }

    // --- Q&A 게시판 관련 메소드 구현 ---
    @Override
    public int getTotalQnaCount(ParameterDTO3 parameterDTO) {
        return qnaboardDAO.getTotalQnaCount(parameterDTO);
    }

    @Override
    public ArrayList<QnaboardDTO> listQnaPage(ParameterDTO3 parameterDTO) {
        return qnaboardDAO.listQnaPage(parameterDTO);
    }

    @Override
    public int insertQuestion(QnaboardDTO dto) {
        return qnaboardDAO.insertQuestion(dto);
    }

    @Override
    public void updateRestStep(QnaboardDTO dto) {
        qnaboardDAO.updateRestStep(dto);
    }

    @Override
    public int insertAnswer(QnaboardDTO dto) {
        return qnaboardDAO.insertAnswer(dto);
    }

    @Override 
    public QnaboardDTO viewQna(int board_Idx) {
        // ⭐️ 수정: viewQna 내부에서 조회수 증가 로직을 제거합니다.
        // 조회수 증가 로직은 Controller의 qnaView 메서드에서만 명시적으로 호출됩니다.
        QnaboardDTO qnaBoardDTO = qnaboardDAO.viewQna(board_Idx);

        if (qnaBoardDTO != null) {
            // 해당 게시글의 bgroup을 이용해 모든 관련 글 (원글 + 답글) 조회
            List<QnaboardDTO> relatedPosts = qnaboardDAO.listAnswersByBgroup(qnaBoardDTO.getBgroup());
            qnaBoardDTO.setAnswers(relatedPosts);
        }
        return qnaBoardDTO;
    }

    // ⭐️ 추가: 조회수 증가 없이 Q&A 게시글 정보를 가져오는 메서드
    @Override
    public QnaboardDTO getQnaByIdWithoutCounting(int board_Idx) {
        QnaboardDTO qnaBoardDTO = qnaboardDAO.viewQna(board_Idx); // DAO의 viewQna는 조회수 증가를 포함하지 않으므로 직접 사용
        if (qnaBoardDTO != null) {
            // 이 메서드에서도 관련 답글은 함께 가져와야 하는 경우가 많으므로 추가
            qnaBoardDTO.setAnswers(qnaboardDAO.listAnswersByBgroup(qnaBoardDTO.getBgroup()));
        }
        return qnaBoardDTO;
    }

    @Override
    public int updateQna(QnaboardDTO dto) {
        return qnaboardDAO.updateQna(dto);
    }

    @Override
    public int deleteQna(int board_Idx) {
        return qnaboardDAO.deleteQna(board_Idx);
    }
}