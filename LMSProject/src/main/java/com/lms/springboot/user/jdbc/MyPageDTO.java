package com.lms.springboot.user.jdbc;

import lombok.Data;

@Data
public class MyPageDTO
{
	// user
		private String user_id;
		private String user_pw;
		private String user_name;
		private String saveFile;
		private String originalFile;
}
