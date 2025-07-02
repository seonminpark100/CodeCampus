package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILectureService
{
	public ArrayList<LectureDTO> list();
	public LectureDTO view(int lecture_id);
}
