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
		String sql = "select * from USER_INFO order by joindate desc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
	}

	@Override
	public int insert(AccountDTO AccountDTO) {
		if (AccountDTO.getJoindate() == null) {
			AccountDTO.setJoindate(LocalDateTime.now());
		}

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into USER_INFO (userid,userpw,username,userbirthdate,joindate,authority,SAVEFILE,ORIGINALFILE) values(?,?,?,?,?,?,EMPTY_BLOB(), EMPTY_BLOB())";
				
				PreparedStatement psmt = con.prepareStatement(sql);
				
				psmt.setString(1, AccountDTO.getUserid());
				psmt.setString(2, AccountDTO.getUserpw());
				psmt.setString(3, AccountDTO.getUsername());
				psmt.setObject(4, AccountDTO.getUserbirthdate());
				psmt.setObject(5, AccountDTO.getJoindate());
				psmt.setObject(6, AccountDTO.getAuthority());
				psmt.setObject(7, AccountDTO.getClass1());
				psmt.setObject(8, AccountDTO.getClass2());
				psmt.setObject(9, AccountDTO.getClass3());
				psmt.setObject(10, AccountDTO.getSavefile());
				psmt.setObject(11, AccountDTO.getOriginalfile());
				return psmt;
			}
		});
		return result;
	}

	@Override
	public AccountDTO selectOne(AccountDTO AccountDTO) {
		String sql = "SELECT userid, userpw, username, userbirthdate ,joindate, authority,  from USER_INFO where userid=?";
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
		StringBuilder sqlBuilder = new StringBuilder("SELECT userid, userpw, username, joindate FROM USER_INFO");
		List<Object> params = new ArrayList<>(); // PreparedStatement에 바인딩할 파라미터들을 저장할 리스트

		String searchField = AccountDTO.getSearchField();
		String searchKeyword = AccountDTO.getSearchKeyword();

		// 검색어가 유효할 경우에만 WHERE 절 추가
		if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
			sqlBuilder.append(" WHERE ");

			// 검색 필드에 따라 조건 동적 추가
			switch (searchField) {
			case "id":
				sqlBuilder.append("userid LIKE ?");
				params.add("%" + searchKeyword + "%");
				break;
			case "name":
				sqlBuilder.append("username LIKE ?");
				params.add("%" + searchKeyword + "%");
				break;
			
			default:
				// searchField가 유효하지 않거나 명시되지 않은 경우, id와 name 모두 검색
				sqlBuilder.append("userid LIKE ? OR username LIKE ?");
				params.add("%" + searchKeyword + "%");
				params.add("%" + searchKeyword + "%");
				break;
			}
		}

		// 결과 정렬 (선택 사항)
		sqlBuilder.append(" ORDER BY joindate DESC");

		// 최종 SQL 쿼리 실행. params.toArray()는 List를 Object[]로 변환하여 PreparedStatement에 바인딩
		return jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
				params.toArray());
	}
}
