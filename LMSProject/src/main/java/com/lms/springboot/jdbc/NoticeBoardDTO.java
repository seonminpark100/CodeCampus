package com.lms.springboot.jdbc;

import java.sql.Date; 
import lombok.Data; 

@Data
public class NoticeBoardDTO {
    private int board_Idx;       
    private String user_Id;  
    private String board_Title;  
    private String board_Content; 
    private Date board_Postdate; 
    private int visitcount;    
    private String category;   
}