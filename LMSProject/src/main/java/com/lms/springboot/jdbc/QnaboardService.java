package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaboardService {

    // --- 기존 공지사항 관련 메소드 ---
    int getTotalNoticeCount(ParameterDTO3 parameterDTO);
    ArrayList<QnaboardDTO> listNoticePage(ParameterDTO3 parameterDTO);
    int insertNotice(QnaboardDTO dto);
    QnaboardDTO viewNotice(int boardIdx);
    int updateNotice(QnaboardDTO dto);
    int deleteNotice(int boardIdx);
    void updateVisitCount(int boardIdx); 

    // --- Q&A 게시판 관련 메소드 ---
    int getTotalQnaCount(ParameterDTO3 parameterDTO);
    ArrayList<QnaboardDTO> listQnaPage(ParameterDTO3 parameterDTO);
    int insertQuestion(QnaboardDTO dto);
    void updateRestStep(QnaboardDTO dto);
    int insertAnswer(QnaboardDTO dto);
    QnaboardDTO viewQna(int boardIdx);
    int updateQna(QnaboardDTO dto);
    int deleteQna(int boardIdx);
}