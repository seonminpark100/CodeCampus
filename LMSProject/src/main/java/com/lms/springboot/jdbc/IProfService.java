package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProfService
{
//	강의
	public ArrayList<ProfDTO> userList(ProfDTO profDTO, String user_id);
	
//	과제(assignment)
	public int getAssignmentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> assignmentBoardListPage(ProfDTO profDTO);
	public ProfDTO assignmentView(ProfDTO profDTO);
	public int assignmentEdit(ProfDTO profDTO);
	public int assignmentDelete(String assignment_idx);
	
	public int lectureUploadProcProf
		(@Param("_title") String title, @Param("_content") String content, @Param("_deadline") String deadline, @Param("_lectureCode") String lecture_code);
	
// 	수강생
	public int getStudentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> studentBoardListPage(ProfDTO profDTO);
	
//	강의실
	public int getLectureTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> lectureBoardListPage(ProfDTO profDTO);
	
	
//	출석부
	public int getAbsentBoardTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> absentBoardListPage(ProfDTO profDTO);

}
