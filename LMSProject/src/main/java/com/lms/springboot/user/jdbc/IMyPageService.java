package com.lms.springboot.user.jdbc;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMyPageService
{
	public MyPageDTO selectOneUser(String user_id);
	public int updateUser(MyPageDTO dto);
}
