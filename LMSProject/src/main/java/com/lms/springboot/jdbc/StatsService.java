// com.lms.springboot.jdbc.StatsService.java

package com.lms.springboot.jdbc; // 이 파일이 맞는지 확인

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTotalLectureCount() {
        System.out.println("DEBUG: StatsService.getTotalLectureCount() 호출됨 (JDBC 방식)");
        String sql = "SELECT COUNT(*) FROM LECTURE";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("DEBUG: 총 강의 수: " + (count != null ? count : 0));
        return count != null ? count : 0;
    }

    public List<Map<String, Object>> getnewLectures(int limit) {
        System.out.println("DEBUG: StatsService.getnewLectures() 호출됨 (JDBC 방식)");
        String sql = "SELECT * FROM (SELECT LECTURE_NAME AS lectureName, " +
                "PROF_ID AS profId, " +
                "LECTURE_START_DATE AS lectureStartDate, " +
                "LECTURE_END_DATE AS lectureEndDate " +
                "FROM LECTURE ORDER BY LECTURE_START_DATE DESC) WHERE ROWNUM <= ?";
        return jdbcTemplate.queryForList(sql, limit);
    }
    
    public int getTotalStudentCount() {
        System.out.println("DEBUG: StatsService.getTotalStudentCount() 호출됨 (JDBC 방식)");
        String sql = "SELECT COUNT(*) FROM USER_INFO WHERE authority = 'ROLE_USER'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("DEBUG: 총 학생 수 (authority 1): " + (count != null ? count : 0));
        return count != null ? count : 0;
    }
}