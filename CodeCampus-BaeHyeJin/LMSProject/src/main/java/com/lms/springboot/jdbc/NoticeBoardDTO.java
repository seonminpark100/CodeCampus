package com.lms.springboot.jdbc;

import java.sql.Date; 
import lombok.Data; 

@Data
public class NoticeBoardDTO {
    private int boardIdx;       
    private String userId;  
    private String boardTitle;  
    private String boardContent; 
    private Date boardPostdate; 
    private int visitcount;    
    private String category;   
}