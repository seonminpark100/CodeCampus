package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface INoticeService {
    // 공지사항 목록 조회 (페이징 및 검색 포함)
    public int getTotalNoticeCount(ParameterDTO2 parameterDTO);
    public ArrayList<NoticeBoardDTO> listNoticePage(ParameterDTO2 parameterDTO);

    // 공지사항 등록
    public int insertNotice(NoticeBoardDTO noticeBoardDTO);

    // 공지사항 상세 보기
    public NoticeBoardDTO viewNotice(int board_Idx); 
    public void updateVisitCount(int board_Idx); 

    // 공지사항 수정
    public int updateNotice(NoticeBoardDTO noticeBoardDTO);

    // 공지사항 삭제
    public int deleteNotice(int board_Idx); 
}