package com.lms.springboot.user.jdbc;

import lombok.Data;

@Data
public class UserListParameterDTO
{
	private String searchField;
	private String searchKeyword;
	
	private int start;
	private int end;
	
	private String user_id;
	
	private String category;
}
