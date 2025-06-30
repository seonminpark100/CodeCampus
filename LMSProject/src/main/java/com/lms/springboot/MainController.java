package com.lms.springboot;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.springboot.jdbc.AccountDTO;
import com.lms.springboot.jdbc.IMemberService;

@Controller
public class MainController
{
	@GetMapping("/")
	public String home()
	{
		return "home";
	}	

	@RequestMapping("/user/index.do")
	public String welcome1() {
		return "user/user";
	}
	
	@RequestMapping("/prof/index.do")
	public String welcome2() {
		return "prof/prof";
	}

	@RequestMapping("/admin/index.do")
	public String welcome3() {
		return "admin/admin";
	}
	@GetMapping("/create/index.do")
	public String welcome4() {
	    return "admin/create"; 
	}
	
	@RequestMapping("/myError.do")
	public String login2() {
		return "auth/error";
	}
	
	@RequestMapping("/denied.do")
	public String login3() {
		return "auth/denied";
	}
//	커스텀 로그인 페이지 매핑
	@RequestMapping("/myLogin.do")
	public String login1(Principal principal, Model model) {
		try {
	        String user_id = null; // 초기화
	        if (principal != null) { // <--- 이 부분이 핵심입니다. principal이 null이 아닌지 확인
	            user_id = principal.getName();
	            model.addAttribute("user_id", user_id);
	            // 여기에 로그인 성공 후 사용자 아이디를 활용한 로직을 추가할 수 있습니다.
	        } else {
	            // principal이 null인 경우 (즉, 로그인되지 않은 상태)
	            // 로그인 페이지를 보여주거나, 필요한 경우 에러 메시지 등을 처리할 수 있습니다.
	            // 현재 코드에서는 그냥 로그인 페이지를 반환하므로 특별히 추가할 로직은 없을 수 있습니다.
	            System.out.println("로그인되지 않은 사용자가 /myLogin.do 에 접근했습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 예외 처리 (주로 예상치 못한 다른 문제 발생 시)
	    }
	    return "auth/login"; // 로그인 폼이 있는 JSP 페이지 경로
	}
	@Autowired
	IMemberService dao;
	@RequestMapping("accountedit/list.do")
	public String member2(Model model, @RequestParam(value = "searchField", required = false) String searchField,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
		List<AccountDTO> memberList;

		if (searchKeyword != null && !searchKeyword.trim().isEmpty()
				|| (searchField != null && !searchField.trim().isEmpty())) {
			AccountDTO searchDTO = new AccountDTO();
			searchDTO.setSearchField(searchField);
			searchDTO.setSearchKeyword(searchKeyword);
			memberList = dao.searchMembers(searchDTO);
		} else {
			memberList = dao.select();
		}

		model.addAttribute("memberList", memberList);
		return "accountedit/list";
	}
	@GetMapping("accountedit/edit.do")
	public String member3(AccountDTO accountDTO, Model model) {
		accountDTO = dao.selectOne(accountDTO);
		model.addAttribute("dto", accountDTO);
		return "accountedit/edit";
	}
//	@RequestMapping(value = "edit.do", method = RequestMethod.POST)
	@PostMapping("accountedit/edit.do")
	public String member7(AccountDTO accountDTO) {
		int result = dao.update(accountDTO);
		if(result==1) System.out.println("수정되었습니다.");
		return "redirect:list.do";
	}
	
	
}
