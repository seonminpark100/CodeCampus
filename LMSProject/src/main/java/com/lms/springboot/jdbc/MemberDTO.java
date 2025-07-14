package com.lms.springboot.jdbc;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
	private String userId;
	private String userPw;
	private String userName;
	private LocalDate userBirthdate;
	private LocalDate joinDate;
	private String authority;
	
}
