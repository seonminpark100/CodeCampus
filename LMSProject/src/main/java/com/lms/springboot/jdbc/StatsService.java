// com.lms.springboot.jdbc.StatsService.java

package com.lms.springboot.jdbc; // 이 파일이 맞는지 확인

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final JdbcTemplate jdbcTemplate;
    
    // PROF_ID를 이름으로 매핑해줌
    private static final Map<String, String> PROF_ID_TO_NAME_MAP = new HashMap<>();
    
    static {
        PROF_ID_TO_NAME_MAP.put("PROF001", "카리나");
        PROF_ID_TO_NAME_MAP.put("PROF002", "윈터");
        PROF_ID_TO_NAME_MAP.put("PROF003", "배혜진");
        PROF_ID_TO_NAME_MAP.put("PROF004", "박선민");
        PROF_ID_TO_NAME_MAP.put("PROF005", "안현준");
    }
    
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
        String sql = "SELECT * FROM (SELECT LECTURE_NAME AS lecture_Name, " +
                "PROF_ID, " +
                "LECTURE_START_DATE, " +
                "LECTURE_END_DATE " +
                "FROM LECTURE ORDER BY LECTURE_START_DATE DESC) WHERE ROWNUM <= ?";
        
        List<Map<String, Object>> lectures = jdbcTemplate.queryForList(sql, limit);

      
        for (Map<String, Object> lecture : lectures) {
            String prof_Id = (String) lecture.get("prof_Id");
            String prof_Name = PROF_ID_TO_NAME_MAP.getOrDefault(prof_Id, "알 수 없음"); // 매핑된 이름이 없으면 "알 수 없음"
            lecture.put("prof_Name", prof_Name); // 새로운 키 'profName'에 이름 추가
        }
        return lectures;
    }
    
    
    public int getTotalStudentCount() {
        System.out.println("DEBUG: StatsService.getTotalStudentCount() 호출됨 (JDBC 방식)");
        String sql = "SELECT COUNT(*) FROM USER_INFO WHERE authority = 'ROLE_USER'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("DEBUG: 총 학생 수 (authority 1): " + (count != null ? count : 0));
        return count != null ? count : 0;
    }
    public List<Map<String, Object>> getTopVisitedBoards(int limit) { // 메서드 이름 변경
        System.out.println("DEBUG: StatsService.getTopVisitedBoards() 호출됨 (JDBC 방식)");
        // VISITCOUNT를 기준으로 내림차순 정렬하여 상위 N개 게시글을 가져옴
        String sql = "SELECT * FROM (SELECT " +
                     "BOARD_TITLE, " +
                     "USER_ID, " +
                     "BOARD_POSTDATE, " +
                     "VISITCOUNT, " + 
                     "BOARD_CONTENT " +
                     "FROM BOARDS " +
                     "ORDER BY VISITCOUNT DESC) " +
                     "WHERE ROWNUM <= ?";
        
        List<Map<String, Object>> topBoards = jdbcTemplate.queryForList(sql, limit);
        return topBoards;
    }
}