package com.lms.springboot.jdbc;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String userId;          
    private String userPw;          
    private String userName;        
    private String userGender;       
    private String userEmail;        
    private String userPhonenum;     
    private String userAddr;         
    private LocalDate userBirthdate; 
    private LocalDate joindate;
    private String authority;
    private String savefile;
    private String originalfile;
    private String majorId;          
    private int enable;

    // 검색 필드 및 키워드 (데이터베이스 컬럼과 직접 매핑되지 않음)
    private String search_Field;
    private String search_Keyword;
}