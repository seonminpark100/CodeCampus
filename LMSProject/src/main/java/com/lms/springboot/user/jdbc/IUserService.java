package com.lms.springboot.user.jdbc;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserService
{	
	public UserFileDTO selectOneFile(String sfile);
}
