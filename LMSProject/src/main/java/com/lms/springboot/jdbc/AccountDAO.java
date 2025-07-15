package com.lms.springboot.jdbc;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository("accountDAO")
public class AccountDAO implements IAccountService {

	@Autowired
    private SqlSession sqlSession;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AccountDTO> select() {
	    // ★★★ 모든 컬럼에 AS 별칭을 명시적으로 추가하여 DTO의 카멜 케이스 필드명과 매핑합니다 ★★★
		String sql = "SELECT USER_ID, USER_PW, USER_NAME, " +
	             "USER_GENDER, USER_EMAIL, " +
	             "USER_PHONENUM, USER_ADDR AS userAddr, " +
	             "USER_BIRTHDATE, JOINDATE, " +
	             "AUTHORITY, SAVEFILE, " +
	             "ORIGINALFILE, MAJOR_ID, ENABLE " + // ★ MAJOR -> MAJOR_ID로 변경
	             "FROM USER_INFO ORDER BY JOINDATE desc";
	    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
	}

	@Override
	public int insert(AccountDTO accountDTO) { // AccountDTO 인자명도 소문자로 변경 (컨벤션)
		if (accountDTO.getJoindate() == null) {
			accountDTO.setJoindate(LocalDate.now());
		}

		int result = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // INSERT 쿼리: 실제 DB 컬럼명 사용 (MAJOR)
				String sql = "INSERT INTO USER_INFO (USER_ID, USER_PW, USER_NAME, USER_GENDER, USER_EMAIL, USER_PHONENUM, USER_ADDR, USER_BIRTHDATE, JOINDATE, AUTHORITY, SAVEFILE, ORIGINALFILE, MAJOR_ID, ENABLE) " + // ★ MAJOR -> MAJOR_ID로 변경
			             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement psmt = con.prepareStatement(sql);

                psmt.setString(1, accountDTO.getUserId());
                psmt.setString(2, accountDTO.getUserPw());
                psmt.setString(3, accountDTO.getUserName());
                psmt.setString(4, accountDTO.getUserGender());
                psmt.setString(5, accountDTO.getUserEmail());
                psmt.setString(6, accountDTO.getUserPhonenum());
                psmt.setString(7, accountDTO.getUserAddr());

                if (accountDTO.getUserBirthdate() != null) {
                    psmt.setDate(8, java.sql.Date.valueOf(accountDTO.getUserBirthdate()));
                } else {
                    psmt.setNull(8, java.sql.Types.DATE);
                }

                if (accountDTO.getJoindate() != null) {
                    psmt.setDate(9, java.sql.Date.valueOf(accountDTO.getJoindate()));
                } else {
                    psmt.setNull(9, java.sql.Types.DATE);
                }

                psmt.setString(10, accountDTO.getAuthority());

                // SAVEFILE, ORIGINALFILE은 String 타입이므로 setString 사용
                if (accountDTO.getSavefile() != null) {
                    psmt.setString(11, accountDTO.getSavefile());
                } else {
                    psmt.setNull(11, java.sql.Types.VARCHAR);
                }

                if (accountDTO.getOriginalfile() != null) {
                    psmt.setString(12, accountDTO.getOriginalfile());
                } else {
                    psmt.setNull(12, java.sql.Types.VARCHAR);
                }

                psmt.setString(13, accountDTO.getMajorId()); // DTO에서는 majorId
                psmt.setInt(14, accountDTO.getEnable());

                return psmt;
            }
        });
        return result;
    }

	@Override
	public AccountDTO selectOne(AccountDTO accountDTO) { // AccountDTO 인자명도 소문자로 변경
		// ★★★ selectOne 메서드에도 AS 별칭을 추가합니다 ★★★
		 String sql = "SELECT USER_ID, USER_PW, USER_NAME, " +
                 "USER_GENDER, USER_EMAIL, " +
                 "USER_PHONENUM, USER_ADDR, " +
                 "USER_BIRTHDATE, JOINDATE, " +
                 "AUTHORITY, SAVEFILE, " +
                 "ORIGINALFILE, MAJOR_ID, ENABLE " +
                 "FROM USER_INFO WHERE USER_ID = ?";
		try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
                    accountDTO.getUserId());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No USER_INFO found with ID: " + accountDTO.getUserId());
            return null;
        }
    }

	@Override
	public int update(AccountDTO accountDTO) {
	    // 기존 데이터를 먼저 조회하여, 파일 정보가 없으면 기존 파일을 유지하도록 함
	    AccountDTO existingAccount = selectOne(accountDTO); // accountDTO.getUserId() 사용

	    String sql = "UPDATE USER_INFO SET USER_PW = ?, USER_NAME = ?, USER_GENDER = ?, " +
	            "USER_EMAIL = ?, USER_PHONENUM = ?, USER_ADDR = ?, USER_BIRTHDATE = ?, " +
	            "AUTHORITY = ?, SAVEFILE = ?, ORIGINALFILE = ?, MAJOR_ID = ?, ENABLE = ? " +
	            "WHERE USER_ID = ?";

	    int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {
	        @Override
	        public void setValues(PreparedStatement ps) throws SQLException {
	            ps.setString(1, accountDTO.getUserPw());
	            ps.setString(2, accountDTO.getUserName());
	            ps.setString(3, accountDTO.getUserGender());
	            ps.setString(4, accountDTO.getUserEmail());
	            ps.setString(5, accountDTO.getUserPhonenum());
	            ps.setString(6, accountDTO.getUserAddr());
	            if (accountDTO.getUserBirthdate() != null) {
	                ps.setDate(7, java.sql.Date.valueOf(accountDTO.getUserBirthdate()));
	            } else {
	                ps.setNull(7, java.sql.Types.DATE);
	            }
	          
	            ps.setString(8, accountDTO.getAuthority());

	          
	            String finalSavefile = accountDTO.getSavefile();
	            if (finalSavefile == null && existingAccount != null) {
	                finalSavefile = existingAccount.getSavefile();
	            }
	            ps.setString(9, finalSavefile);

	            // 10. ORIGINALFILE: accountDTO에 originalfile이 없으면 기존 existingAccount의 originalfile 사용
	            String finalOriginalfile = accountDTO.getOriginalfile();
	            if (finalOriginalfile == null && existingAccount != null) {
	                finalOriginalfile = existingAccount.getOriginalfile();
	            }
	            ps.setString(10, finalOriginalfile);

	            ps.setString(11, accountDTO.getMajorId());
	            ps.setInt(12, accountDTO.getEnable());
	            ps.setString(13, accountDTO.getUserId());
	        }
	    });
	    return result;
	}

	@Override
	public int delete(AccountDTO accountDTO) { // AccountDTO 인자명도 소문자로 변경
		String sql = "DELETE FROM USER_INFO WHERE USER_ID=?";
		int result = jdbcTemplate.update(sql, new Object[] { accountDTO.getUserId() }); // userId
		return result;
	}

	@Override
    public List<AccountDTO> searchMembers(AccountDTO accountDTO) {
        StringBuilder sqlBuilder = new StringBuilder(
           
            "SELECT USER_ID, USER_PW, USER_NAME, " +
            "USER_GENDER, USER_EMAIL, " +
            "USER_PHONENUM, " +
            "USER_ADDR, USER_BIRTHDATE, " +
            "JOINDATE, AUTHORITY, " +
            "SAVEFILE, ORIGINALFILE, " +
            "MAJOR_ID, ENABLE " + 
            "FROM USER_INFO");
        List<Object> params = new ArrayList<>();

        String search_Field = accountDTO.getSearch_Field();
        String search_Keyword = accountDTO.getSearch_Keyword();

        if (search_Keyword != null && !search_Keyword.trim().isEmpty()) {
            sqlBuilder.append(" WHERE ");
            switch (search_Field) {
            case "userId":
                sqlBuilder.append("USER_ID LIKE ?");
                params.add("%" + search_Keyword + "%");
                break;
            case "userName":
                sqlBuilder.append("USER_NAME LIKE ?");
                params.add("%" + search_Keyword + "%");
                break;
            default:
                sqlBuilder.append("USER_ID LIKE ? OR USER_NAME LIKE ?");
                params.add("%" + search_Keyword + "%");
                params.add("%" + search_Keyword + "%");
                break;
            }
        }
        sqlBuilder.append(" ORDER BY JOINDATE DESC");
        return jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class),
                params.toArray());
    }

    @Override
    public int updateEnableStatus(String userId, int enable) {
        String sql = "UPDATE USER_INFO SET ENABLE=? WHERE USER_ID=?";
        return jdbcTemplate.update(sql, enable, userId);
    }
    @Override
    public int updateProfileImage(String userId, String saveFileName, String originalFileName) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_Id", userId);         
        params.put("savefile", saveFileName);     
        params.put("originalfile", originalFileName); 
        return sqlSession.update("com.lms.springboot.jdbc.IAccountService.updateProfileImage", params);
    }
}