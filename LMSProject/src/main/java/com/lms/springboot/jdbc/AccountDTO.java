package com.lms.springboot.jdbc;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class AccountDTO {
	private String userid; 
    private String userpw; 
    private String username; 
    private LocalDateTime userbirthdate; 
    private LocalDateTime joindate; 
    private String authority; 
                               

    private String class1; 
    private String class2; 
    private String class3; 

    private byte[] savefile; 
    private byte[] originalfile; 
    
    private String searchField;
	private String searchKeyword;
}
