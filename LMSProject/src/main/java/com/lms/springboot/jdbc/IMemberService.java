package com.lms.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IMemberService {
	public List<MemberDTO> select();
	public int insert(MemberDTO memberDTO);
	public MemberDTO selectOne(MemberDTO memberDTO);
	public int update(MemberDTO memberDTO);
	public int delete(MemberDTO memberDTO);
}
