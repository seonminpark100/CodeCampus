package com.lms.springboot.jdbc;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaboardDTO {
	 private int boardIdx;
	    private String userId;
	    private String lectureCode;
	    private String boardTitle;
	    private String boardContent;
	    private Date boardPostdate;
	    private int downcount;
	    private int visitcount;
	    private String category;
	    private int bgroup;
	    private int bstep;
	    private int bindent;
	    private int boardLike;
}
