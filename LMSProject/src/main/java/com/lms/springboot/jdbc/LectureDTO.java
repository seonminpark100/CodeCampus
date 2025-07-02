package com.lms.springboot.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class LectureDTO
{
	private int lecture_id;
	private String lecture_name;
	private String prof_id;
	private String prof_name;
	private Date lecture_start_date;
	private Date lecture_end_date;
}
