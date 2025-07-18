package com.lms.springboot.prof;

import lombok.Data;

@Data
public class ProfDTO
{
//	private String idx;
//	private String name;
//	private String title;
//	private String content;
//	private java.sql.Date postdate;
	
//	Absents (DO NOT DELETE !!)
	private String data;
	private String date;
	
//	Boards table
	private String board_idx;
	private String user_id;
	private String lecture_code;
	private String board_title;
	private String board_content;
	private java.sql.Date board_postdate;
	private Object ofile;
	private String sfile;
	private int downcount;
	private int visitcount;
	private String category;
	private int bgroup;
	private int bstep;
	private int bindent;
	private int board_like;
	
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
	
//	Files table
	private String file_idx;
	
//	USER_INTO table
	private java.sql.Date joindate;
	private String user_pw;
	private java.sql.Date user_birthdate;
	private String authority;
	private String savefile;
	private String originalfile;
	private String major_id;
	private String enable;
	
//	Lecture table
	private String lecture_id;
	private String lecture_name;
	private String prof_id;
	private java.sql.Date lecture_start_date;
	private java.sql.Date lecture_end_date;
	
//	ABSENTS table 	
	private String user_major;
	private String user_name;
	private String absent_state;
	private String origin_image;
	private String saved_image;
	private java.sql.Date lecture_date;
	private java.sql.Date attendance_time;
	
	// Search stuff
	private String searchField;
	private String searchKeyword;
	
	// the start and end of Pagination
	private int start;
	private int end;
	
}
