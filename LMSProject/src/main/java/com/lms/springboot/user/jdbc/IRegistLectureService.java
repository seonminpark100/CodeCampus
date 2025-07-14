package com.lms.springboot.user.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRegistLectureService
{
	public int getTotalLectureCount(UserListParameterDTO param);
	public ArrayList<RegistLectureDTO> selectAllLecturePage(UserListParameterDTO param);
	public int duplicationEnrollCheck(RegistLectureDTO dto);
	public int insertEnroll(RegistLectureDTO dto);
}
