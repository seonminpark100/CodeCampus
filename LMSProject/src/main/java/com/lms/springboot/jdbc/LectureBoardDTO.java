package com.lms.springboot.jdbc;

import java.sql.Date;

import lombok.Data;

@Data
public class LectureBoardDTO
{
	 private String lecture_Idx;      
	 private String lecture_Name;     
	 private String prof_Id;          
	 private Date lecture_Start_Date;  
	 private Date lecture_End_Date;    
	 private String lecture_Code;     
	 private String major_Id;         
}