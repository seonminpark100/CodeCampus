package com.lms.springboot.user.jdbc;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAssignmentDTO
{
	private String user_id;
	private String user_name;

	private String major_name;
	
	private int lecture_idx;
	private String lecture_name;
	private String lecture_code;
	
	private int assignment_idx;
	private String assignment_title;
	private String assignment_content;
	private Date uploaded_date;
	private Date deadline;
		
	private int assignment_submit_idx;
	private String assignment_content_s;
	private String assignment_comment;
	private String assignment_ofile;
	private String assignment_sfile;
	private int score;	
}

