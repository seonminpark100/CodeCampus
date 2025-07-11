package com.lms.springboot.jdbc;

import java.util.List;


public interface IAccountService {
	public List<AccountDTO> select();
	public int insert(AccountDTO accountDTO);
	public AccountDTO selectOne(AccountDTO accountDTO);
	public int update(AccountDTO accountDTO);
	public int delete(AccountDTO accountDTO);

    
    public List<AccountDTO> searchMembers(AccountDTO accountDTO);
    int updateEnableStatus(String userId, int enable);
    
    public int updateProfileImage(String userId, String saveFileName, String originalFileName);
}
