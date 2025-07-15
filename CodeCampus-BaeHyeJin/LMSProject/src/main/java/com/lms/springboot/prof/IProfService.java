package com.lms.springboot.prof;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface IProfService
{
//	Students List
	public ArrayList<ProfDTO> userList(ProfDTO profDTO, String user_id);
	public int getStudentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> studentBoardListPage(ProfDTO profDTO);
	public ArrayList<ProfDTO> selectNoticeBoardList(NoticeBoardDTO DTO); 
	public ArrayList<ProfDTO> selectCommunityList(NoticeBoardDTO DTO); 
	
	public MypageDTO selectOneUser(String user_id);
	public int updateUser(MypageDTO dto);
}
