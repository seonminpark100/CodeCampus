package com.lms.springboot.prof.lecture;

import lombok.Data;

@Data
public class LectureDTO
{	
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
	
//	Files table
	private String file_idx;
	
//	USER_INTO table
	private java.sql.Date joindate;
	private String user_name;
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
	
	// Search stuff
	private String searchField;
	private String searchKeyword;
	
	// the start and end of Pagination
	private int start;
	private int end;
	
}
