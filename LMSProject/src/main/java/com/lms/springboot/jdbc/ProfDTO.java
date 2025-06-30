package com.lms.springboot.jdbc;

import lombok.Data;

@Data
public class ProfDTO
{
	private String idx;
	private String name;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private int visitcount;
	
	//검색어 관련 멤버변수 
	private String searchField;
	private String searchKeyword;
	
	//게시물의 구간을 표현하는 멤버변수 
	private int start;
	private int end;
}
