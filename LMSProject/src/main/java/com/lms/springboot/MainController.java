package com.lms.springboot;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.AccountDTO;
import com.lms.springboot.jdbc.IMemberService;

@Controller
public class MainController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	IMemberService dao;

    MainController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    } // IMemberService 인터페이스 주입

	@GetMapping("/")
	public String home() {
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

	@RequestMapping("/myLogin.do")
	public String login1(Principal principal, Model model) {
		try {
			String user_id = null;
			if (principal != null) {
				user_id = principal.getName();
				model.addAttribute("user_id", user_id);
			} else {
				System.out.println("로그인되지 않은 사용자가 /myLogin.do 에 접근했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "auth/login"; // 로그인 폼이 있는 JSP 페이지 경로
	}

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

	@PostMapping("accountedit/edit.do")
	public String member7(@ModelAttribute AccountDTO accountDTO, RedirectAttributes redirectAttributes) {
		
		 System.out.println("DEBUG: accountDTO.getUserGender(): " + accountDTO.getUserGender());
	   
	    if (accountDTO.getUserPw() != null && !accountDTO.getUserPw().isEmpty() &&
	        !bCryptPasswordEncoder.matches(accountDTO.getUserPw(), dao.selectOne(accountDTO).getUserPw())) { 
	        accountDTO.setUserPw(bCryptPasswordEncoder.encode(accountDTO.getUserPw()));
	    } else {
	        accountDTO.setUserPw(dao.selectOne(accountDTO).getUserPw());
	    }

	    int result = dao.update(accountDTO);
	    if (result == 1) {
	        System.out.println("수정되었습니다.");
	        redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "회원 정보 수정에 실패했습니다.");
	    }
	    return "redirect:list.do";
	}

	@RequestMapping("/delete.do")
	public String member4(AccountDTO accountDTO, RedirectAttributes redirectAttributes) { // RedirectAttributes 추가
		int result = dao.delete(accountDTO);
		if (result == 1) {
			System.out.println("삭제되었습니다.");
			redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
		} else {
			redirectAttributes.addFlashAttribute("message", "삭제에 실패했습니다.");
		}
		return "redirect:/accountedit/list.do";
	}

	@GetMapping("/admin/create")
	public String showCreateAccountForm() {
		return "admin/create";
	}

	// 새 회원 계정 생성 폼 제출 처리
	@PostMapping("/admin/create") // 이 부분이 회원가입 처리 경로입니다.
	public String createAccount(@ModelAttribute AccountDTO accountDTO, 
			@RequestParam(value = "ofile", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			System.out.println("Received file: " + (file != null ? file.getOriginalFilename() : "null"));
			System.out.println("File is empty: " + (file != null ? file.isEmpty() : "N/A"));

		
			if (file != null && !file.isEmpty()) { 
				accountDTO.setSavefile(file.getBytes());
				accountDTO.setOriginalfile(file.getBytes());
			} else { 
				
				accountDTO.setSavefile(null);
				accountDTO.setOriginalfile(null);
				System.out.println("INFO: 파일이 첨부되지 않았습니다. NULL로 처리합니다.");
			}

			if (accountDTO.getJoindate() == null) {
				accountDTO.setJoindate(LocalDate.now());
			}

			if (accountDTO.getUserBirthdate() != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
				String initialPassword = accountDTO.getUserBirthdate().format(formatter);
                // 비밀번호를 암호화하여 저장
				accountDTO.setUserPw(bCryptPasswordEncoder.encode(initialPassword)); // ★★★ 비밀번호 암호화 적용 ★★★
				System.out.println("DEBUG: 초기 패스워드 설정됨 (생년월일): " + initialPassword);
			} else {
				System.err.println("ERROR: userBirthdate가 null이어서 초기 패스워드를 설정할 수 없습니다.");
				redirectAttributes.addFlashAttribute("message", "생년월일이 없어 초기 패스워드 설정에 실패했습니다.");
				return "redirect:/admin/create";
			}
			// ★★★ 여기까지 패스워드 설정 로직 추가 ★★★

			int result = dao.insert(accountDTO);

			if (result == 1) {
				redirectAttributes.addFlashAttribute("message", "계정 생성에 성공했습니다. 초기 패스워드는 생년월일입니다.");
			} else {
				redirectAttributes.addFlashAttribute("message", "계정 생성에 실패했습니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "파일 처리 중 오류가 발생했습니다.");
			return "redirect:/admin/create";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "계정 생성 중 오류가 발생했습니다.");
			return "redirect:/admin/create";
		}
		return "redirect:/accountedit/list.do";
	}

	@PostMapping("/accountedit/toggleEnable.do") // ★★★ 이전에 제안했던 매핑 경로 수정 적용 ★★★
	public String toggleEnable(@RequestParam("userId") String userId, @RequestParam("enable") int enable, // 변경될 enable
																											// 값 (0 또는
																											// 1)
			RedirectAttributes redirectAttributes) {

		try {

			int result = dao.updateEnableStatus(userId, enable);

			if (result > 0) {
				redirectAttributes.addFlashAttribute("message", "회원 상태가 성공적으로 변경되었습니다.");
			} else {
				redirectAttributes.addFlashAttribute("message", "회원 상태 변경에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "회원 상태 변경 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/accountedit/list.do";
	}
	
}