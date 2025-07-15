package com.lms.springboot.jdbc;

import lombok.Data;

@Data
public class ParameterDTO3 {
    // 검색어 관련 멤버변수 
    private String search_Field;
    private String search_Keyword;
    private String lecture_Code; // 강의 코드를 통한 검색도 가능하도록 추가

    // 게시물의 구간을 표현하는 멤버변수 (페이징)
    private int start;
    private int end;

    // Q&A 게시판의 고정 카테고리
    private String category = "Q"; // 기본값을 "Q"로 설정하여 명확하게
}