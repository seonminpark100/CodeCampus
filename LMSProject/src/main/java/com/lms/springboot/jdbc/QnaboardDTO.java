package com.lms.springboot.jdbc;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaboardDTO {
	 	private int board_Idx;
	    private String user_Id;
	    private String lecture_Code;
	    private String board_Title;
	    private String board_Content;
	    private Date board_Postdate;
	    private int downcount;
	    private int visitcount;
	    private String category;
	    private int bgroup;
	    private int bstep;
	    private int bindent;
	    private int board_Like;
	    
	    private List<QnaboardDTO> answers;
	    
	    public List<QnaboardDTO> getAnswers() {
	        return answers;
	    }

	    public void setAnswers(List<QnaboardDTO> answers) {
	        this.answers = answers;
	    }
}
