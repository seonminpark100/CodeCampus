package com.lms.springboot.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserService
{
	// 전체 강의 관련
	public int getTotalLectureCount(UserListParameterDTO param);
	public ArrayList<UserDTO> selectAllLecture();
	public ArrayList<UserDTO> selectAllLecturePage(UserListParameterDTO param);
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
	
	// 수강 신청
	public int duplicationEnrollCheck(UserDTO dto);
	public int insertEnroll(UserDTO dto);
	
	// 과제 관련
	public ArrayList<UserDTO> selectAllMyAssignmentList(UserListParameterDTO param);
//	public UserDTO selectOneAssignment(UserDTO dto);
	public UserDTO selectOneAssignment(int assignment_idx);
	public int insertAssignmentSubmit(UserDTO dto);
	public UserDTO selectOneAssignmentSubmit(String user_id);
	public int submitCheck(UserDTO dto);
	
	
	// Board 관련 (강의, 커뮤니티)
	public ArrayList<UserDTO> selectAllBoardListPage(UserListParameterDTO param);
//	public UserDTO selectOneLMSBoard(int board_idx);
	public int insertBoard(UserDTO dto);
	public int updateBoard(UserDTO dto);
	public int getTotalBoardCount(UserListParameterDTO param);
	
	// files
	public int insertFiles(List<UserDTO> list);
	public int insertFile(UserDTO dto);
	public ArrayList<UserDTO> selectFiles(int board_idx);
	public UserDTO selectOneFile(String sfile);
}
