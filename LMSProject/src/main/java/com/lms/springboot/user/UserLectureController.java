package com.lms.springboot.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.springboot.user.jdbc.IUserLectureService;
import com.lms.springboot.user.jdbc.UserFileDTO;
import com.lms.springboot.user.jdbc.UserLectureDTO;
import com.lms.springboot.utils.FileUtil;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/lecture")
public class UserLectureController
{
	@Autowired
	IUserLectureService dao;
	
	@GetMapping("/myLectureList.do")
	public String myLectureList(Model model, @AuthenticationPrincipal UserDetails ud) {
		ArrayList<UserLectureDTO> list = dao.selectAllMyLecture(ud.getUsername());
		model.addAttribute("list", list);
		return "user/lecture/myLectureList";
	}
	
	@GetMapping("/lectureList.do")
	public String lecture(Model model, UserLectureDTO dto, @AuthenticationPrincipal UserDetails ud, HttpServletResponse resp) {
		ArrayList<UserLectureDTO> list = dao.selectLectureSessionList(dto.getLecture_code());
		model.addAttribute("list", list);
		UserLectureDTO lecture = dao.selectOneLecture(dto.getLecture_code());
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lecture/lectureList";
	}
	
	@RequestMapping("/lectureView.do")
	public String lectureView(Model model, UserLectureDTO dto) {
		dao.increaseVisitCount(dto.getBoard_idx());
		dto.setCategory("L");
		dto = dao.selectOneBoard(dto.getBoard_idx());
		dto.setBoard_content(dto.getBoard_content().replaceAll("\r\n", "<br/>"));
		ArrayList<UserFileDTO> files = dao.selectFiles(dto.getBoard_idx());
		ArrayList<UserFileDTO> videoList = new ArrayList<>();
		ArrayList<UserFileDTO> fileList = new ArrayList<>();
		List<String> videoExt = Arrays.asList("mp4", "mov", "wmv", "avi", "mkv");
		
		for(UserFileDTO file : files) {
			
			if(videoExt.contains(file.getSFile().substring(file.getSFile().lastIndexOf(".") + 1).toLowerCase())) { // 파일이 비디오라면
				videoList.add(file);
			} else { // 파일이 비디오가 아니라면
				fileList.add(file);
			}
		}
		model.addAttribute("video", FileUtil.getVideoFile(videoList));
		model.addAttribute("file", FileUtil.getFiles(fileList)); 
		
		model.addAttribute("dto", dto);
		UserLectureDTO lecture = dao.selectOneLecture(dto.getLecture_code());		
		model.addAttribute("lecture_name", lecture.getLecture_name());
		model.addAttribute("user_name", lecture.getUser_name());
		return "user/lecture/lectureView";
	}
}
