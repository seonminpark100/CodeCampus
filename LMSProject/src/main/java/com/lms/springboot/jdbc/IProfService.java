package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfService
{
//	내 강의 목록 가져오기
	public ArrayList<ProfDTO> myLectureList(ProfDTO profDTO);
	
//	사용자
	public ArrayList<ProfDTO> userList(ProfDTO profDTO);
//	강의실
	public int getLectureTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> lectureBoardListPage(ProfDTO profDTO);
	
//	공지사항
	public int getNoticeBoardTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> noticeBoardListPage(ProfDTO profDTO);
	
//	출석부
	public int getAbsentBoardTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> absentBoardListPage(ProfDTO profDTO);

	public int writeAbsentBoard(ProfDTO profDTO);
}
