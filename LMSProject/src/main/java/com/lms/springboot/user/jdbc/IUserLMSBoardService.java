package com.lms.springboot.user.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserLMSBoardService
{
	public int getTotalBoardCount(UserListParameterDTO param);
	public ArrayList<UserLMSBoardDTO> selectAllBoardListPage(UserListParameterDTO param);
	public int increaseVisitCount(int board_idx);
	public UserLMSBoardDTO selectOneBoard(int board_idx);
	public ArrayList<UserFileDTO> selectFiles(int board_idx);
	public int insertBoard(UserLMSBoardDTO dto);
	public int insertFiles(List<UserFileDTO> list);
	public int updateBoard(UserLMSBoardDTO dto);
	public int getTotalFilesCount(int board_idx);
	public int deleteFiles(int board_idx);
	public int deleteBoard(int board_idx);
	public int updateBStep(UserLMSBoardDTO dto);
	public int insertReply(UserLMSBoardDTO dto);
	public int getTotalReplyCount(int bGroup);
	public int isReplied(UserLMSBoardDTO dto);
}
