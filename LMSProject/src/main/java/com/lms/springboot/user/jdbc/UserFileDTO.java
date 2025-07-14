package com.lms.springboot.user.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFileDTO
{
	private int board_idx;

	private String oFile;
	private String sFile;
	
	private int file_idx;
}
