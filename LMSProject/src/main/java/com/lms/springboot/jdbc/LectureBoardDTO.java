package com.lms.springboot.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class LectureBoardDTO
{
	 private String lectureIdx;      
	 private String lectureName;     
	 private String profId;          
	 private Date lectureStartDate;  
	 private Date lectureEndDate;    
	 private String lectureCode;     
	 private String majorId;         
}