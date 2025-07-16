package com.lms.springboot.prof.assignment;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProfAssignmentService
{

//	Assignment - Proffessor's side
	public int getAssignmentTotalCount(AssignmentDTO DTO);
	public ArrayList<AssignmentDTO> assignmentBoardListPage(AssignmentDTO DTO);
	public AssignmentDTO assignmentView(AssignmentDTO DTO);
	public int assignmentEdit( AssignmentDTO DTO);
	public int assignmentDelete(String assignment_idx);
	public int assignmentlectureUploadProcProf
	(@Param("_title") String title, @Param("_content") String content,
			@Param("_deadline") String deadline, @Param("_lectureCode") String lecture_code);
		
//	Assignment - Student's side
	public int getSubmittedAssignmentTotalCount(AssignmentDTO DTO);
	public ArrayList<AssignmentDTO> submittedAssignmentBoardListPage(AssignmentDTO DTO);
	public AssignmentDTO submittedAssignmentView(AssignmentDTO DTO);
	public int submittedAssignmentGetSocreProc(@Param("_score") String score, @Param("_lectureCode") String lecture_code,
			@Param("_assignment_comment") String assignment_comment, @Param("_assignment_submit_idx") String assignment_submit_idx);
	
	
	
	
}
