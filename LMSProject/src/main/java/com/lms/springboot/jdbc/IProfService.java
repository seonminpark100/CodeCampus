package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfService
{
//	강의 리스트
	public ArrayList<ProfDTO> userList(ProfDTO profDTO, String user_id);
	
// 	수강생 리스트
	public int getStudentTotalCount(ProfDTO profDTO, String lectureId);
	public ArrayList<ProfDTO> studentBoardListPage(ProfDTO profDTO, String lectureId);
	
//	강의실
	public int getLectureTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> lectureBoardListPage(ProfDTO profDTO);
	
//	공지사항
	public int getNoticeBoardTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> noticeBoardListPage(ProfDTO profDTO);
	
//	출석부
	public int getAbsentBoardTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> absentBoardListPage(ProfDTO profDTO);

}
