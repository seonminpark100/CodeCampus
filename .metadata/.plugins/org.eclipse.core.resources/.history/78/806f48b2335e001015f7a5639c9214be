package com.lms.springboot.prof;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfService
{
//	수강생 목록
	public ArrayList<ProfDTO> userList(ProfDTO profDTO, String user_id);

// 	수강생
	public int getStudentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> studentBoardListPage(ProfDTO profDTO);
}
