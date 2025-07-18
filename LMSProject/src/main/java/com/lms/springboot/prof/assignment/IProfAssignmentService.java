package com.lms.springboot.prof.assignment;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lms.springboot.prof.ProfDTO;

@Mapper
public interface IProfAssignmentService
{

//	Assignment - Proffessor's side
	public int getAssignmentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> assignmentBoardListPage(ProfDTO profDTO);
	public ProfDTO assignmentView(ProfDTO profDTO);
	public int assignmentEdit(ProfDTO profDTO);
	public int assignmentDelete(String assignment_idx);
	public int assignmentlectureUploadProcProf
	(@Param("_title") String title, @Param("_content") String content,
			@Param("_deadline") String deadline, @Param("_lectureCode") String lecture_code);
		
//	Assignment - Student's side
	public int getSubmittedAssignmentTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> submittedAssignmentBoardListPage(ProfDTO profDTO);
	public ProfDTO submittedAssignmentView(ProfDTO profDTO);
	public int submittedAssignmentGetSocreProc(@Param("_score") String score, @Param("_lectureCode") String lecture_code,
			@Param("_assignment_comment") String assignment_comment, @Param("_assignment_submit_idx") String assignment_submit_idx);
	
	
	
	
}
