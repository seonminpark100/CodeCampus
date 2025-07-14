package com.lms.springboot.prof.lecture;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.lms.springboot.prof.ProfDTO;

@Mapper
public interface IProfLectureService
{

//	강의
	public int getLectureTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> lectureBoardListPage(ProfDTO profDTO);
	public ProfDTO lectureUploadPage(ProfDTO profDTO);
	public int insertLectureWithFile(ProfDTO profDTO);
	public int insertLectureWithoutFile(ProfDTO profDTO);
//	public ProfDTO lectureView(ProfDTO profDTO);
	public ProfDTO lectureViewWithoutFile(ProfDTO profDTO);
	public ArrayList<ProfDTO> lectureViewWithFile(ProfDTO profDTO);
	public int lectureEditBoards(ProfDTO profDTO);
	public int lectureDeleteBoards(String board_idx);
	public int lectureDeleteFiles(String board_idx);
	public int lectureEditDeleteFiles(String board_idx);
	public int insertLectureEditWithFile(ProfDTO profDTO);
	
}
