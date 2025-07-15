package com.lms.springboot.jdbc;

import java.util.List;
import org.apache.ibatis.annotations.Mapper; // ★★★ @Mapper 어노테이션 필수!
import org.apache.ibatis.annotations.Param;

@Mapper // 이 인터페이스가 MyBatis 매퍼임을 명시합니다.
public interface IAccountService {
	public List<AccountDTO> select();
	public int insert(AccountDTO accountDTO);
	public AccountDTO selectOne(AccountDTO accountDTO);
	public int update(AccountDTO accountDTO);
	public int delete(AccountDTO accountDTO);

    public List<AccountDTO> searchMembers(AccountDTO accountDTO);
    int updateEnableStatus(@Param("userId") String userId, @Param("enable") int enable);

    int updateProfileImage(@Param("userId") String userId, @Param("saveFileName") String saveFileName, @Param("originalFileName") String originalFileName);
}