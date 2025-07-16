package com.lms.springboot.prof.absent;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lms.springboot.prof.ProfDTO;

@Mapper
public interface IProfAbsentService
{
//	For Attendance
	public int absentProcProf
	(@Param("_user_id") String user_id, @Param("_absent_state") String absent_state,
			@Param("_lecture_date") String lecture_date, 
			@Param("_lectureCode") String lecture_code);
	
	public int getStudentTotalCount(AbsentDTO DTO);
	public ArrayList<ProfDTO> studentBoardListPage(AbsentDTO DTO);
}
