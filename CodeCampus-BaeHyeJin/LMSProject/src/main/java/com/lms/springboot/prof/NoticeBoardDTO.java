package com.lms.springboot.prof;

import lombok.Data;

@Data
public class NoticeBoardDTO
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
	
}
