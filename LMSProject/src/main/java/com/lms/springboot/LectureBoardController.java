package com.lms.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LectureBoardController {
	
	@Autowired
	
	@GetMapping("/notview")
	@ResponseBody
	public String main() {
		return "view 없이 컨트롤러에서 즉시 출력";
	}
}
