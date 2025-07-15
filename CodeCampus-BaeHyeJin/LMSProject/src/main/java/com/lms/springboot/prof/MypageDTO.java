package com.lms.springboot.prof;

import lombok.Data;

@Data
public class MypageDTO
{
//	USER_INTO table
	private String user_id;
	private String user_pw;
	private String user_name;
	private String saveFile;
	private String originalFile;
}
