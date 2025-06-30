package com.lms.springboot.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO implements IMemberService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<AccountDTO> select() {
		String sql = "SELECT * FROM USER_INFO ORDER BY joindate desc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
	}

	@Override
	public int insert(AccountDTO AccountDTO) {
		if (AccountDTO.getJoindate() == null) {
		    AccountDTO.setJoindate(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
		}

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into USER_INFO (userid, userpw, username, userbirthdate, joindate, authority, class1, class2, class3, SAVEFILE, ORIGINALFILE) values(?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement psmt = con.prepareStatement(sql);
				
				psmt.setString(1, AccountDTO.getUserid());
	            psmt.setString(2, AccountDTO.getUserpw());
	            psmt.setString(3, AccountDTO.getUsername());
	            psmt.setObject(4, AccountDTO.getUserbirthdate()); // java.sql.Date
	            psmt.setObject(5, AccountDTO.getJoindate());     // java.sql.Date
	            psmt.setString(6, AccountDTO.getAuthority()); // String 타입에 맞춰 setString
	            psmt.setString(7, AccountDTO.getClass1());
	            psmt.setString(8, AccountDTO.getClass2());
	            psmt.setString(9, AccountDTO.getClass3());

	            // --- BLOB 데이터 처리 로직 적용 (필수) ---
	            // SAVEFILE (10번째 파라미터)
	            if (AccountDTO.getSavefile() != null) {
	                psmt.setBytes(10, AccountDTO.getSavefile());
	            } else {
	                psmt.setNull(10, java.sql.Types.BLOB);
	            }

	            // ORIGINALFILE (11번째 파라미터)
	            if (AccountDTO.getOriginalfile() != null) {
	                psmt.setBytes(11, AccountDTO.getOriginalfile());
	            } else {
	                psmt.setNull(11, java.sql.Types.BLOB);
	            }
	          
				return psmt;
			}
		});
		return result;
	}

	@Override
	public AccountDTO selectOne(AccountDTO AccountDTO) {
		String sql = "SELECT userid, userpw, username, userbirthdate, joindate, authority, class1, class2, class3, SAVEFILE, ORIGINALFILE FROM USER_INFO WHERE userid=?";
	    try {
	        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
	                AccountDTO.getUserid());
	    } catch (EmptyResultDataAccessException e) {
	        System.out.println("No USER_INFO found with ID: " + AccountDTO.getUserid());
	        return null;
	    }
	}


	@Override
	public int update(AccountDTO AccountDTO) {
		String sql = "update USER_INFO set userpw=?, username=? where userid=?";
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, AccountDTO.getUserpw());     
			    ps.setString(2, AccountDTO.getUsername());  
			    ps.setString(3, AccountDTO.getUserid());   
			}
		});
		return result;
	}
	@Override
	public int delete(AccountDTO AccountDTO) {
		String sql = "Delete FROM USER_INFO WHERE userid=?";
		int result = jdbcTemplate.update(sql, new Object[] {AccountDTO.getUserid()});
		return result;
	}

	@Override
	public List<AccountDTO> searchMembers(AccountDTO AccountDTO) {
		 // 모든 컬럼을 조회하도록 SQL 변경
		StringBuilder sqlBuilder = new StringBuilder("SELECT userid, userpw, username, userbirthdate, joindate, authority, CLASS1, CLASS2, CLASS3, SAVEFILE, ORIGINALFILE FROM USER_INFO");
	    List<Object> params = new ArrayList<>();

	    String searchField = AccountDTO.getSearchField();
	    String searchKeyword = AccountDTO.getSearchKeyword();

	    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
	        sqlBuilder.append(" WHERE ");
	        switch (searchField) {
	            case "userid":
	                sqlBuilder.append("userid LIKE ?");
	                params.add("%" + searchKeyword + "%");
	                break;
	            case "username":
	                sqlBuilder.append("username LIKE ?");
	                params.add("%" + searchKeyword + "%");
	                break;
	            default:
	                sqlBuilder.append("userid LIKE ? OR username LIKE ?");
	                params.add("%" + searchKeyword + "%");
	                params.add("%" + searchKeyword + "%");
	                break;
	        }
	    }
	    sqlBuilder.append(" ORDER BY joindate DESC");
	    return jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
	            params.toArray());
	}
		
}
