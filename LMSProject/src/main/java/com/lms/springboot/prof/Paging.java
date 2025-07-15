package com.lms.springboot.prof;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public class Paging
{

	public static void paging(HttpServletRequest req, Model model, int totalCount, int pageSize, int blockPage, int pageNum) {
		try
		{

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
