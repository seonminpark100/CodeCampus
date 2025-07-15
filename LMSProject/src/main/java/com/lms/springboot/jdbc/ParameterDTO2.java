package com.lms.springboot.jdbc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDTO2
{
	//검색어 관련 멤버변수 
	private String search_Field;
	private String search_Keyword;
	
	//게시물의 구간을 표현하는 멤버변수 
	private int start;
	private int end;
}
