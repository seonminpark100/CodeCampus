package com.lms.springboot.prof.lecture;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfLectureService
{

//	Lecture
	public int getLectureTotalCount(LectureDTO DTO);
	public ArrayList<LectureDTO> lectureBoardListPage(LectureDTO DTO);
	public LectureDTO lectureUploadPage(LectureDTO DTO);
	public int insertLectureWithFile(LectureDTO DTO);
	public int insertLectureWithoutFile(LectureDTO DTO);
	public LectureDTO lectureViewWithoutFile(LectureDTO DTO);
	public ArrayList<LectureDTO> lectureViewWithFile(LectureDTO DTO);
	public int lectureEditBoards(LectureDTO DTO);
	public int lectureDeleteBoards(String board_idx);
	public int lectureDeleteFiles(String board_idx);
	public int lectureEditDeleteFiles(String board_idx);
	public int insertLectureEditWithFile(LectureDTO DTO);
	
}
