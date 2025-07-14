package com.lms.springboot.user.jdbc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserAssignmentService
{
	public ArrayList<UserAssignmentDTO> selectAllMyAssignmentList(UserListParameterDTO param);
	public int submitCheck(UserAssignmentDTO dto);
	public UserAssignmentDTO selectOneAssignment(int assignment_idx);
	public int insertAssignmentSubmit(UserAssignmentDTO dto);
	public UserAssignmentDTO selectOneAssignmentSubmit(UserAssignmentDTO dto);
	public UserAssignmentDTO selectOneAssignmentSubmitBySubmitIdx(int assignment_submit_idx);
	public int updateAssignmentSubmit(UserAssignmentDTO dto);
	public int canSubmit(int assignment_idx);
}
