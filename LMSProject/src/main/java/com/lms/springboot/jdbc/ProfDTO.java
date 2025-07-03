package com.lms.springboot.jdbc;

import lombok.Data;

@Data
public class ProfDTO
{
	
//	테이블 테스트 용 멤버변수
	private String idx;
	private String name;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private int visitcount;

//	USER_INTO 테이블
	private String CLASS_1;
	private String CLASS_2;
	private String CLASS_3;
	private java.sql.Date JOINDATE;
	
//	Lecture 테이블
	private String LECTURE_ID;
	private String LECTURE_NAME;
	private String PROF_ID;
	private String PROF_NAME;
	private java.sql.Date LECTURE_START_DATE;
	private java.sql.Date LECTURE_END_DATE;
	
//	ABSENTS 테이블 
	private String SURVEY_ID;
//	private String LECTURE_NAME; // Lecture에서 가져오기
	private String USER_MAJOR;
	private String USER_ID;
	private String USER_NAME;
	private String ABSENT_STATE;
	private String ORIGIN_IMAGE;
	private String SAVED_IMAGE;
	private java.sql.Date LECTURE_DATE;
	private java.sql.Date ATTENDANCE_TIME;
	
	
	//검색어 관련 멤버변수 
	private String searchField;
	private String searchKeyword;
	//게시물의 구간을 표현하는 멤버변수 
	private int start;
	private int end;
	
}
