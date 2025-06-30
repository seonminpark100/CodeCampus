package com.lms.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface IMemberService {
	public List<MemberDTO> select();
	MemberDTO selectOne(MemberDTO memberDTO); 
	int insert(MemberDTO memberDTO); // 회원 추가
	int update(MemberDTO memberDTO); // 회원 정보 업데이트
	int delete(MemberDTO memberDTO); // 회원 삭제
	 // 회원 가입 시 기본 권한을 부여하기 위한 메서드 (필요하다면)
    void insertUserAuthority(@Param("userId") String userId, @Param("authority") String authority);
	public MemberDTO selectOne(String username);
}
