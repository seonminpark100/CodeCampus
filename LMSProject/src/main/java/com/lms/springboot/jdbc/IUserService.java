package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserService
{
	// 전체 강의 관련
	public ArrayList<UserDTO> selectAllLecture();
	public ArrayList<UserDTO> selectAllMyLecture(String user_id);
	public UserDTO selectOneLecture(String lecture_code);
	
	// 유저관련
	public ArrayList<UserDTO> selectAllUser();
	public UserDTO selectOneUser(String user_id);
	public int updateUser(UserDTO dto);
	
	// 강의 관련
	public ArrayList<UserDTO> selectLectureSessionList(String lecture_code);
//	public int validateUser(UserDTO dto);
	public UserDTO selectOneBoard(UserDTO dto);
}
