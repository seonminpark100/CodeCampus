package com.lms.springboot.prof.assignment;

import lombok.Data;

@Data
public class AssignmentDTO
{
//	Assignment table
	private String assignment_idx;
	private String assignment_title;
	private String assignment_content;
	private String assignment_code;
	private java.sql.Date uploaded_date;
	private java.sql.Date deadline;
	
//	Assignment_submit table
	private String assignment_submit_idx;
	private String assignment_content_s;
	private String assignment_comment;
	private String assignment_ofile;
	private String assignment_sfile;
	private String score;
	private java.sql.Date submitted_date;
	
//	USER_INTO table
	private java.sql.Date joindate;
	private String user_pw;
	private java.sql.Date user_birthdate;
	private String authority;
	private String savefile;
	private String originalfile;
	private String major_id;
	private String enable;
	private String user_id;
	private String lecture_code;
	private String user_name;
	
//	Lecture table
	private String lecture_id;
	private String lecture_name;
	private String prof_id;
	private java.sql.Date lecture_start_date;
	private java.sql.Date lecture_end_date;
	
	// Search stuff
	private String searchField;
	private String searchKeyword;
	
	// the start and end of Pagination
	private int start;
	private int end;
	
}
