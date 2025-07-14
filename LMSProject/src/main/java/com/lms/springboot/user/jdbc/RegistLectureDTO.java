package com.lms.springboot.user.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class RegistLectureDTO
{
	private String user_id;
	private String user_pw;
	private String user_name;
	private String major_name;	
	private int lecture_idx;
	private String lecture_name;
	private String prof_id;
	private Date lecture_start_date;
	private Date lecture_end_date;
	private String lecture_code;
}
