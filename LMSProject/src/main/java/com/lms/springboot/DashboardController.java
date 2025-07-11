package com.lms.springboot; 

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lms.springboot.jdbc.StatsService; 

@Controller
public class DashboardController {

    @Autowired
    private StatsService statsService; 

    @GetMapping({"admin/dashboard"}) 
    public String adminDashboard(Model model) {

        // 1. 전체 강의 수 가져오기
        int totalLectureCount = statsService.getTotalLectureCount();
        model.addAttribute("totalLectureCount", totalLectureCount);
        System.out.println("DEBUG: 총 강의 수: " + totalLectureCount);

        // 2. 신규 강의 목록 가져오기 (최신 5개)
        List<Map<String, Object>> newLectures = statsService.getnewLectures(5);
        model.addAttribute("newLectures", newLectures);
     //    System.out.println("DEBUG: 신규 강의 목록 (총 " + (newLectures != null ? newLectures.size() : 0) + "개): " + newLectures);
        
        int totalStudentCount = statsService.getTotalStudentCount();
        model.addAttribute("totalStudentCount", totalStudentCount);
     //   System.out.println("DEBUG: 총 학생 수 (authority 1): " + totalStudentCount);

        List<Map<String, Object>> topVisitedBoards = statsService.getTopVisitedBoards(3); // 메서드 호출 변경
        model.addAttribute("topVisitedBoards", topVisitedBoards); // 속성 이름 변경
      //  System.out.println("DEBUG: 조회수가 많은 게시글 (총 " + (topVisitedBoards != null ? topVisitedBoards.size() : 0) + "개): " + topVisitedBoards);

        return "admin/dashboard"; 
    }
}