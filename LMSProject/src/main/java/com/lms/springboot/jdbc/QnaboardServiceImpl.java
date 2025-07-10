package com.lms.springboot.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

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
    public QnaboardDTO viewNotice(int boardIdx) {
        // 조회수 증가 로직은 Controller나 Service에서 처리될 수 있습니다. 여기서는 DAO 호출만 예시
        updateVisitCount(boardIdx); // 조회수 증가 서비스 메서드 호출 (선택적)
        return qnaboardDAO.viewNotice(boardIdx);
    }

    @Override
    public int updateNotice(QnaboardDTO dto) {
        return qnaboardDAO.updateNotice(dto);
    }

    @Override
    public int deleteNotice(int boardIdx) {
        return qnaboardDAO.deleteNotice(boardIdx);
    }

    @Override
    public void updateVisitCount(int boardIdx) {
        qnaboardDAO.updateVisitCount(boardIdx);
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
    public QnaboardDTO viewQna(int boardIdx) {
        updateVisitCount(boardIdx); // 조회수 증가 서비스 메서드 호출 (선택적)
        return qnaboardDAO.viewQna(boardIdx);
    }

    @Override
    public int updateQna(QnaboardDTO dto) {
        return qnaboardDAO.updateQna(dto);
    }

    @Override
    public int deleteQna(int boardIdx) {
        return qnaboardDAO.deleteQna(boardIdx);
    }
}