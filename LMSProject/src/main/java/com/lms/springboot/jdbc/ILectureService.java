package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface ILectureService 
{
	// 강의 목록 조회 (페이징 및 검색 포함)
    public int getTotalLectureCount(ParameterDTO parameterDTO);
    public ArrayList<LectureBoardDTO> listLecturePage(ParameterDTO parameterDTO);

    // @Param 어노테이션 사용 X, DTO를 파라미터로 받도록 변경
    public int insertLecture(LectureBoardDTO lectureBoardDTO);
    // 강의 상세 보기
    public LectureBoardDTO viewLecture(LectureBoardDTO lectureBoardDTO);
    // 강의 수정
    public int updateLecture(LectureBoardDTO lectureBoardDTO);
    // 강의 삭제
    public int deleteLecture(String lecture_Idx); // 컨트롤러에서 @RequestParam으로 받을 것이므로 String 타입 매개변수
}