package com.lms.springboot.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class AccountDAO implements IMemberService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AccountDTO> select() {
	    // ★★★ 이 부분을 수정합니다 ★★★
		String sql = "SELECT USER_ID, USER_PW, USER_NAME, USER_GENDER, USER_EMAIL, " +
	             "USER_PHONENUM AS userPhoneNum, " +
	             "USER_ADDR, USER_BIRTHDATE, JOINDATE, AUTHORITY, SAVEFILE, ORIGINALFILE, MAJOR_ID AS majorId, ENABLE " + // <--- 이 부분을 MAJOR_ID로 변경
	             "FROM USER_INFO ORDER BY JOINDATE desc";
	    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
	}

	@Override
	public int insert(AccountDTO AccountDTO) {
		if (AccountDTO.getJoindate() == null) {
			AccountDTO.setJoindate(LocalDate.now());
		}

		int result = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO USER_INFO (USER_ID, USER_PW, USER_NAME, USER_GENDER, USER_EMAIL, USER_PHONENUM, USER_ADDR, USER_BIRTHDATE, JOINDATE, AUTHORITY, SAVEFILE, ORIGINALFILE, MAJOR, ENABLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement psmt = con.prepareStatement(sql);

				psmt.setString(1, AccountDTO.getUserId());
				psmt.setString(2, AccountDTO.getUserPw());
				psmt.setString(3, AccountDTO.getUserName());
				psmt.setString(4, AccountDTO.getUserGender());
				psmt.setString(5, AccountDTO.getUserEmail());
				psmt.setString(6, AccountDTO.getUserPhoneNum());
				psmt.setString(7, AccountDTO.getUserAddr());

				if (AccountDTO.getUserBirthdate() != null) {
					psmt.setDate(8, java.sql.Date.valueOf(AccountDTO.getUserBirthdate()));
				} else {
					psmt.setNull(8, java.sql.Types.DATE);
				}

				if (AccountDTO.getJoindate() != null) {
					psmt.setDate(9, java.sql.Date.valueOf(AccountDTO.getJoindate()));
				} else {
					psmt.setNull(9, java.sql.Types.DATE);
				}

				psmt.setString(10, AccountDTO.getAuthority());

				if (AccountDTO.getSavefile() != null) {
					psmt.setBytes(11, AccountDTO.getSavefile());
				} else {
					psmt.setNull(11, java.sql.Types.BLOB);
				}

				if (AccountDTO.getOriginalfile() != null) {
					psmt.setBytes(12, AccountDTO.getOriginalfile());
				} else {
					psmt.setNull(12, java.sql.Types.BLOB);
				}
				
				psmt.setString(13, AccountDTO.getMajorId());
				psmt.setInt(14, AccountDTO.getEnable());


				return psmt;
			}
		});
		return result;
	}

	@Override
	public AccountDTO selectOne(AccountDTO AccountDTO) {
		String sql = "SELECT USER_ID, USER_PW, USER_NAME, USER_GENDER, USER_EMAIL, USER_PHONENUM AS userPhoneNum, USER_ADDR, USER_BIRTHDATE, JOINDATE, AUTHORITY, SAVEFILE, ORIGINALFILE, MAJOR_ID, ENABLE FROM USER_INFO WHERE USER_ID=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
					AccountDTO.getUserId());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("No USER_INFO found with ID: " + AccountDTO.getUserId());
			return null;
		}
	}

	@Override
	public int update(AccountDTO AccountDTO) {
		String sql = "UPDATE USER_INFO SET USER_PW=?, USER_NAME=?, USER_GENDER=?, USER_EMAIL=?, USER_PHONENUM=?, USER_ADDR=?, MAJOR_ID=?, ENABLE=? WHERE USER_ID=?";
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, AccountDTO.getUserPw());
				ps.setString(2, AccountDTO.getUserName());
				ps.setString(3, AccountDTO.getUserGender());
				ps.setString(4, AccountDTO.getUserEmail());
				ps.setString(5, AccountDTO.getUserPhoneNum());
				ps.setString(6, AccountDTO.getUserAddr());
				ps.setString(7, AccountDTO.getMajorId());
				ps.setInt(8, AccountDTO.getEnable());
				ps.setString(9, AccountDTO.getUserId());
			}
		});
		return result;
	}

	@Override
	public int delete(AccountDTO AccountDTO) {
		String sql = "DELETE FROM USER_INFO WHERE USER_ID=?";
		int result = jdbcTemplate.update(sql, new Object[] { AccountDTO.getUserId() });
		return result;
	}

	@Override
	public List<AccountDTO> searchMembers(AccountDTO AccountDTO) {
		 StringBuilder sqlBuilder = new StringBuilder(
			        "SELECT USER_ID, USER_PW, USER_NAME, USER_GENDER, USER_EMAIL, " +
			        "USER_PHONENUM AS userPhoneNum, " +
			        "USER_ADDR, USER_BIRTHDATE, JOINDATE, AUTHORITY, SAVEFILE, ORIGINALFILE, MAJOR AS majorId, ENABLE " + 
			        "FROM USER_INFO");
	    List<Object> params = new ArrayList<>();

		String searchField = AccountDTO.getSearchField();
		String searchKeyword = AccountDTO.getSearchKeyword();

		if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
	        sqlBuilder.append(" WHERE ");
	        switch (searchField) {
	        case "userId":
	            sqlBuilder.append("USER_ID LIKE ?");
	            params.add("%" + searchKeyword + "%");
	            break;
	        case "userName":
	            sqlBuilder.append("USER_NAME LIKE ?");
	            params.add("%" + searchKeyword + "%");
	            break;
	        default:
	            sqlBuilder.append("USER_ID LIKE ? OR USER_NAME LIKE ?");
	            params.add("%" + searchKeyword + "%");
	            params.add("%" + searchKeyword + "%");
	            break;
	        }
	    }
	    sqlBuilder.append(" ORDER BY JOINDATE DESC");
	    return jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
	            params.toArray());
	}
	@Override // IMemberService 인터페이스를 구현합니다.
    public int updateEnableStatus(String userId, int enable) {
        String sql = "UPDATE USER_INFO SET ENABLE=? WHERE USER_ID=?";
        return jdbcTemplate.update(sql, enable, userId);
    }
}