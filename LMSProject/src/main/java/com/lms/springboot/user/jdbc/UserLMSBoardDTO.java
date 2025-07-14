package com.lms.springboot.user.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class UserLMSBoardDTO
{
	// user
	private String user_id;
	private String user_name;
//	private String saveFile;
//	private String originalFile;
//	private String major_id;

	// major
//	private String major_name;
	
	// lecture
//	private int lecture_idx;
//	private String lecture_name;
//	private String prof_id;
//	private Date lecture_start_date;
//	private Date lecture_end_date;
	private String lecture_code;
//	
	// board
	private int board_idx;
	private String board_title;
	private String board_content;
	private Date board_postdate;
	private int visitCount;
	private String category;
	private int bGroup;
	private int bStep;
	private int bIndent;
}
