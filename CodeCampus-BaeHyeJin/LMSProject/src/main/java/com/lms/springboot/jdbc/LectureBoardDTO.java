package com.lms.springboot.jdbc;


import lombok.Data;

@Data
public class LectureBoardDTO
{
//	 private String lectureIdx;      
//	 private String lectureName;     
//	 private String profId;          
//	 private Date lectureStartDate;  
//	 private Date lectureEndDate;    
//	 private String lectureCode;     
//	 private String majorId;     
	 
	private String lecture_name;
	private String prof_id;
	private java.sql.Date lecture_start_date;
	private java.sql.Date lecture_end_date;
	private String major_id;
	private String lecture_idx;
	private String lecture_code;
	
}