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

    @Override // <--- 여기를 수정합니다.
    public QnaboardDTO viewQna(int boardIdx) {
        // 1. 기존 게시글 상세 조회
        QnaboardDTO qnaBoardDTO = qnaboardDAO.viewQna(boardIdx);

        if (qnaBoardDTO != null) {
            // 2. 조회수 증가 로직 (기존 위치)
            updateVisitCount(boardIdx);

            // 3. 해당 게시글의 bgroup을 이용해 모든 관련 글 (원글 + 답글) 조회
            // 이 메서드를 호출하려면 먼저 QnaboardDAO 인터페이스에 listAnswersByBgroup 메서드가 선언되어 있어야 합니다.
            List<QnaboardDTO> relatedPosts = qnaboardDAO.listAnswersByBgroup(qnaBoardDTO.getBgroup());

            // 4. 조회된 답글 목록을 qnaBoardDTO 객체에 설정 (QnaboardDTO에 setAnswers() 메서드가 있어야 합니다.)
            qnaBoardDTO.setAnswers(relatedPosts);
        }
        return qnaBoardDTO;
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