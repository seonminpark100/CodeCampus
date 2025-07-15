package com.lms.springboot.jdbc;

import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList; 
import java.util.List;     

@Mapper 
public interface QnaboardDAO {


    // 총 공지사항 수를 가져오는 메서드
    int getTotalNoticeCount(ParameterDTO3 parameterDTO);

    // 공지사항 목록을 페이징하여 가져오는 메서드
    ArrayList<QnaboardDTO> listNoticePage(ParameterDTO3 parameterDTO);

    // 새 공지사항을 삽입하는 메서드
    int insertNotice(QnaboardDTO dto);

    // 특정 공지사항을 조회하는 메서드
    QnaboardDTO viewNotice(int boardIdx);

    // 공지사항을 업데이트하는 메서드
    int updateNotice(QnaboardDTO dto);

    // 방문 횟수를 업데이트하는 메서드 (Q&A와 공지사항 공통으로 사용될 수 있음)
    void updateVisitCount(int boardIdx);

    // 특정 공지사항을 삭제하는 메서드 (이전에 언급되지 않았지만, 보통 필요함)
    int deleteNotice(int boardIdx);


    // --- Q&A 게시판 관련 메소드 (QnaboardService에 정의되어 있고 DAO에서 구현될 것임) ---
    // 총 Q&A 게시글 수를 가져오는 메서드 (QnaboardController에서 사용됨)
    int getTotalQnaCount(ParameterDTO3 parameterDTO);

    // Q&A 게시글 목록을 페이징하여 가져오는 메서드 (QnaboardController에서 사용됨)
    ArrayList<QnaboardDTO> listQnaPage(ParameterDTO3 parameterDTO);

    // 새 질문을 삽입하는 메서드
    int insertQuestion(QnaboardDTO dto);

    // 답변 작성 시 기존 게시글들의 bstep을 조정하는 메서드
    void updateRestStep(QnaboardDTO dto);

    // 답변을 삽입하는 메서드
    int insertAnswer(QnaboardDTO dto);

    // 특정 Q&A 게시글을 조회하는 메서드
    QnaboardDTO viewQna(int boardIdx);

    // Q&A 게시글을 업데이트하는 메서드
    int updateQna(QnaboardDTO dto);

    // Q&A 게시글을 삭제하는 메서드
    int deleteQna(int boardIdx);
    

    // 새롭게 추가할 메서드 선언
    List<QnaboardDTO> listAnswersByBgroup(int bgroup);
   
}