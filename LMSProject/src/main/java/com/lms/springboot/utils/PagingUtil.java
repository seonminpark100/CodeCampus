package com.lms.springboot.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.lms.springboot.prof.ProfDTO;

import jakarta.servlet.http.HttpServletRequest;

public class PagingUtil
{
	public static String pagingImg(
		int totalRecordCount,
		int pageSize,
		int blockPage,
		int pageNum,
		String page) {
		
		String pagingStr = "";
		
		//1. Find total page counts
		int totalPage = (int)(Math.ceil(((double)totalRecordCount/pageSize)));
		
		/*2. Find the page by current page.
		*/
		int intTemp = 
			(((pageNum-1) / blockPage) * blockPage) + 1;
		
		//3. Move to first page and previous page
		if(intTemp != 1) {
			pagingStr += ""
				+ "<a href='"+page+"pageNum=1'>"
				+ "<img src='./../images/paging1.gif'></a>";
			pagingStr += "&nbsp;";
			pagingStr += ""
				+ "<a href='"+page+"pageNum="+
								(intTemp-blockPage)+"'>"
				+ "<img src='./../images/paging2.gif'></a>";
		}
		
		// for control page counts
		int blockCount = 1;
		/*
		4. the logic for handling pagination.
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
		
		//5. Move to next block and last page 
		if(intTemp <= totalPage) {
			pagingStr += "<a href='"+page+"pageNum="+
											intTemp+"'>"
				+ "<img src='./../images/paging3.gif'></a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+page+"pageNum="+
											totalPage+"'>"
				+ "<img src='./../images/paging4.gif'></a>";
		}
		
		return pagingStr;
	}
	
	
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
	
	
}
