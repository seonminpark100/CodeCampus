package com.lms.springboot.user.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class UserDTO
{
	// user
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_email;
	private String user_phoneNum;
	private String user_addr;
	private Date user_birthdate;
	private Date joinDate;
	private String authority;
	private String saveFile;
	private String originalFile;
	private String major_id;
	int enable;

	// major
	private String major_name;
	
	// lecture
	private int lecture_idx;
	private String lecture_name;
	private String prof_id;
	private Date lecture_start_date;
	private Date lecture_end_date;
	private String lecture_code;
	
	// board
	private int board_idx;
	private String board_title;
	private String board_content;
	private Date board_postdate;
	private int downCount;
	private int visitCount;
	private String category;
	private int bGroup;
	private int bStep;
	private int bIndent;
	private int board_like;
	
	// assignment
	private int assignment_idx;
	private String assignment_title;
	private String assignment_content;
	private Date uploaded_date;
	private Date deadline;
	
	// assignmen_submit
	private int assignment_submit_idx;
	private String assignment_content_s;
	private String assignment_comment;
	private String assignment_ofile;
	private String assignment_sfile;
	private int score;	
	
	// file
	private String ofile;
	private String sfile;
}
