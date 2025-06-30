package com.lms.springboot.jdbc;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public interface IMemberService {
	public List<AccountDTO> select();
	public int insert(AccountDTO accountDTO);
	public AccountDTO selectOne(AccountDTO accountDTO);
	public int update(AccountDTO accountDTO);
	public int delete(AccountDTO accountDTO);

    
    public List<AccountDTO> searchMembers(AccountDTO accountDTO);
}
