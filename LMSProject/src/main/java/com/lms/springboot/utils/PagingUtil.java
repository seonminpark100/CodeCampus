package com.lms.springboot.utils;

<<<<<<< HEAD
=======
<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.lms.springboot.prof.ProfDTO;

import jakarta.servlet.http.HttpServletRequest;

=======
>>>>>>> master
>>>>>>> origin/master
public class PagingUtil
{
	public static String pagingImg(
		int totalRecordCount,
		int pageSize,
		int blockPage,
		int pageNum,
		String page) {
		
		String pagingStr = "";
		
<<<<<<< HEAD
=======
<<<<<<< HEAD
		//1. Find total page counts
		int totalPage = (int)(Math.ceil(((double)totalRecordCount/pageSize)));
		
		/*2. Find the page by current page.
=======
>>>>>>> origin/master
		//1.전체페이지 구하기
		int totalPage = (int)(Math.ceil(((double)totalRecordCount/pageSize)));
		
		/*2.현재페이지번호를 통해 이전 페이지블럭에
		해당하는 페이지를 구한다.
<<<<<<< HEAD
=======
>>>>>>> master
>>>>>>> origin/master
		*/
		int intTemp = 
			(((pageNum-1) / blockPage) * blockPage) + 1;
		
<<<<<<< HEAD
=======
<<<<<<< HEAD
		//3. Move to first page and previous page
		if(intTemp != 1) {
			pagingStr += ""
				+ "<a href='"+page+"pageNum=1'>"
				+ "<img src='./../images/paging1.gif'></a>";
=======
>>>>>>> origin/master
		//3.처음페이지 바로가기 & 이전페이지블럭 바로가기
		if(intTemp != 1) {
			//첫번째 페이지 블럭에서는 출력되지 않음
			//두번째 페이지 블럭부터 출력됨.
			pagingStr += ""
				+ "<a href='"+page+"pageNum=1'>"
<<<<<<< HEAD
				+ "<img src='./images/paging1.gif'></a>";
=======
				+ "<img src='/images/paging1.gif'></a>";
>>>>>>> master
>>>>>>> origin/master
			pagingStr += "&nbsp;";
			pagingStr += ""
				+ "<a href='"+page+"pageNum="+
								(intTemp-blockPage)+"'>"
<<<<<<< HEAD
				+ "<img src='./images/paging2.gif'></a>";
=======
<<<<<<< HEAD
				+ "<img src='./../images/paging2.gif'></a>";
		}
		
		// for control page counts
		int blockCount = 1;
		/*
		4. the logic for handling pagination.
=======
				+ "<img src='/images/paging2.gif'></a>";
>>>>>>> origin/master
		}
		
		//페이지표시 제어를 위한 변수
		int blockCount = 1;
		/*
		4.페이지를 뿌려주는 로직 : blockPage의 수만큼 또는
			마지막페이지가 될때까지 페이지를 출력한다.
<<<<<<< HEAD
=======
>>>>>>> master
>>>>>>> origin/master
		*/
		while(blockCount<=blockPage && intTemp<=totalPage)
		{
			if(intTemp==pageNum) {
				pagingStr += "&nbsp;"+intTemp+"&nbsp;";
			}
			else {
				pagingStr += "&nbsp;<a href='"+page
					+"pageNum="+intTemp+"'>"+
					intTemp+"</a>&nbsp;";
			}
			intTemp++;
			blockCount++;
		}
		
<<<<<<< HEAD
=======
<<<<<<< HEAD
		//5. Move to next block and last page 
		if(intTemp <= totalPage) {
			pagingStr += "<a href='"+page+"pageNum="+
											intTemp+"'>"
				+ "<img src='./../images/paging3.gif'></a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+page+"pageNum="+
											totalPage+"'>"
				+ "<img src='./../images/paging4.gif'></a>";
=======
>>>>>>> origin/master
		//5.다음페이지블럭 & 마지막페이지 바로가기
		if(intTemp <= totalPage) {
			pagingStr += "<a href='"+page+"pageNum="+
											intTemp+"'>"
<<<<<<< HEAD
				+ "<img src='./images/paging3.gif'></a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+page+"pageNum="+
											totalPage+"'>"
				+ "<img src='./images/paging4.gif'></a>";
=======
				+ "<img src='/images/paging3.gif'></a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+page+"pageNum="+
											totalPage+"'>"
				+ "<img src='/images/paging4.gif'></a>";
>>>>>>> master
>>>>>>> origin/master
		}
		
		return pagingStr;
	}
<<<<<<< HEAD
=======
<<<<<<< HEAD
	
	
	public static void paging(HttpServletRequest req, ProfDTO profDTO, Model model, int totalCount, int pageSize, int blockPage, int pageNum) {
		try
		{
//			set start and end value
			int start = (pageNum-1) * pageSize + 1;
			int end = pageNum * pageSize;
			profDTO.setStart(start);
			profDTO.setEnd(end);
			
			// Save the virtual number at Map 
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("totalCount", totalCount);
			maps.put("pageSize", pageSize);
			maps.put("pageNum", pageNum);
			model.addAttribute("maps", maps);
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	
=======
>>>>>>> master
>>>>>>> origin/master
}
