package com.lms.springboot.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfService
{
	public int getTotalCount(ProfDTO profDTO);
	public ArrayList<ProfDTO> listPage(ProfDTO profDTO);
}
