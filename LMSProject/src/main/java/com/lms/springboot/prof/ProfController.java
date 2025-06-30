package com.lms.springboot.prof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.jdbc.IProfService;
import com.lms.springboot.jdbc.ProfDTO;
import com.lms.springboot.utils.PagingUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfController
{
	@Autowired
	IProfService dao;
	
	@RequestMapping("/prof/noticeboard.do")
	public String boardList(Model model, HttpServletRequest req, 
			ProfDTO profDTO) 
	{
		int totalCount = dao.getTotalCount(profDTO);
		int pageSize = 2; 
		int blockPage = 2; 
		int pageNum = (req.getParameter("pageNum")==null 
			|| req.getParameter("pageNum").equals("")) 
			? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
		//현제 페이지에 출력할 게시물의 구간을 계산한다. 
		int start = (pageNum-1) * pageSize + 1;
		int end = pageNum * pageSize;
		//계산의 결과는 DTO에 저장한다. 
		profDTO.setStart(start);
		profDTO.setEnd(end);
		
		//View에서 게시물의 가상번호 계산을 위한 값을 Map에 저장 
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("totalCount", totalCount);
		maps.put("pageSize", pageSize);
		maps.put("pageNum", pageNum);
		model.addAttribute("maps", maps);
		
		//DB에서 인출한 게시물의 목록을 Model에 저장 
		ArrayList<ProfDTO> lists = dao.listPage(profDTO);
		model.addAttribute("lists", lists);
		
		//게시판 하단에 출력할 페이지번호를 String으로 저장한 후 Model에 저장
		String pagingImg =
			PagingUtil.pagingImg(totalCount, pageSize, 
				blockPage, pageNum,
				req.getContextPath()+"/prof/noticeboard.do?");
		model.addAttribute("pagingImg", pagingImg);
		
		
		return "prof/list";       
	}
}
