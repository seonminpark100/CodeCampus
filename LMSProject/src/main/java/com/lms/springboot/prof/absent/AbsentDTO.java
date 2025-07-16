package com.lms.springboot.prof.absent;

import lombok.Data;

@Data
public class AbsentDTO
{

//	Absents (DO NOT DELETE !!)
	private String data;
	private String date;
	
//	USER_INTO table
	private java.sql.Date joindate;
	private String user_pw;
	private java.sql.Date user_birthdate;
	private String authority;
	private String savefile;
	private String originalfile;
	private String major_id;
	private String enable;
	private String lecture_code;
	
//	ABSENTS table 	
	private String user_major;
	private String user_id;
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
