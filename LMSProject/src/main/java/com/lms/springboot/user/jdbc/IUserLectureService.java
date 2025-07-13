package com.lms.springboot.user.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserLectureService
{
	public ArrayList<UserLectureDTO> selectAllMyLecture(String user_id);
	public ArrayList<UserLectureDTO> selectLectureSessionList(String lecture_code);
	public UserLectureDTO selectOneLecture(String lecture_code);
	public int increaseVisitCount(int board_idx);
	public UserLectureDTO selectOneBoard(int dto);
	public ArrayList<UserFileDTO> selectFiles(int board_idx);
}
