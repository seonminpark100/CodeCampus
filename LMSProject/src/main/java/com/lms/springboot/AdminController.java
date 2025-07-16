package com.lms.springboot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; // ★★★ 추가
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.AccountDTO;
import com.lms.springboot.jdbc.IAccountService;



@Controller
@RequestMapping("/admin") 
public class AdminController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("accountDAO") 
    IAccountService dao;

    public AdminController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

   
    @GetMapping("") 
    public String redirectToDashboard() {
    	return "redirect:/admin/dashboard"; 
    }

    @RequestMapping("/accountedit/list.do")
    public String member2(Model model,
                          @RequestParam(value = "search_Field", required = false) String search_Field,
                          @RequestParam(value = "search_Keyword", required = false) String search_Keyword) {
        System.out.println("### DEBUG: AdminController - member2 (accountedit/list.do) 메서드 진입! ###");
        List<AccountDTO> memberList;

        // 검색 키워드가 유효할 때만 검색 로직을 수행
        if (search_Keyword != null && !search_Keyword.trim().isEmpty()) {
            AccountDTO searchDTO = new AccountDTO();
            searchDTO.setSearch_Field(search_Field); // searchField는 null일 수 있으므로 DAO에서 기본값 처리
            searchDTO.setSearch_Keyword(search_Keyword.trim()); // 공백 제거
            memberList = dao.searchMembers(searchDTO);
        } else {
            // 검색 키워드가 없으면 전체 목록 조회
            memberList = dao.select();
        }
        model.addAttribute("memberList", memberList);
        return "accountedit/list";
    }

    @GetMapping("/accountedit/edit.do")
    public String member3(AccountDTO accountDTO, Model model) {
    	System.out.println("### DEBUG: AdminController - member3 (accountedit/edit.do) 메서드 진입! ###");
        accountDTO = dao.selectOne(accountDTO);
        if (accountDTO != null) {
            System.out.println("### DEBUG: selectOne 결과: " + accountDTO.getUserId() + " 회원 정보 로드됨 ###");
        } else {
            System.out.println("### DEBUG: selectOne 결과: 회원 정보 로드 실패 (null) ###");
        }
        model.addAttribute("dto", accountDTO);
        return "accountedit/edit";
    }

    @PostMapping("/accountedit/edit.do")
    public String member7(@ModelAttribute AccountDTO accountDTO, RedirectAttributes redirectAttributes) {
    	 System.out.println("### DEBUG: AdminController - member7 (accountedit/edit.do - POST) 메서드 진입! ###");
        System.out.println("DEBUG: accountDTO.getUserGender(): " + accountDTO.getUserGender());

        AccountDTO existingAccount = dao.selectOne(accountDTO);
        if (existingAccount == null) {
            redirectAttributes.addFlashAttribute("message", "회원 정보를 찾을 수 없어 수정에 실패했습니다.");
            return "redirect:/admin/accountedit/list.do";
        }

        String existingEncodedPassword = existingAccount.getUserPw();
        if (accountDTO.getUserPw() != null && !accountDTO.getUserPw().isEmpty()) {
            if (!bCryptPasswordEncoder.matches(accountDTO.getUserPw(), existingEncodedPassword)) {
                accountDTO.setUserPw(bCryptPasswordEncoder.encode(accountDTO.getUserPw()));
            } else {
                accountDTO.setUserPw(existingEncodedPassword);
            }
        } else {
            accountDTO.setUserPw(existingEncodedPassword);
        }

        int result = dao.update(accountDTO);
        if (result == 1) {
            System.out.println("수정되었습니다.");
            redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "회원 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/accountedit/list.do";
    }


    @RequestMapping("/accountedit/delete.do")
    public String member4(AccountDTO accountDTO, RedirectAttributes redirectAttributes) {
    	 System.out.println("### DEBUG: AdminController - member4 (accountedit/delete.do) 메서드 진입! ###");
        int result = dao.delete(accountDTO);
        if (result == 1) {
            System.out.println("삭제되었습니다.");
            redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "삭제에 실패했습니다.");
        }
        return "redirect:/admin/accountedit/list.do";
    }


    @GetMapping("/create")
    public String showCreateAccountForm() {
    	 System.out.println("### DEBUG: AdminController - showCreateAccountForm 메서드 진입! ###");
        return "admin/create";
    }


    @PostMapping("/create")
    public String createAccount(@ModelAttribute AccountDTO accountDTO,
                                RedirectAttributes redirectAttributes) {
    	 System.out.println("### DEBUG: AdminController - createAccount (POST) 메서드 진입! ###");
        try {
            accountDTO.setSavefile(null);
            accountDTO.setOriginalfile(null);

            if (accountDTO.getJoindate() == null) {
                accountDTO.setJoindate(LocalDate.now());
            }

            if (accountDTO.getUserBirthdate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String initialPassword = accountDTO.getUserBirthdate().format(formatter);
                accountDTO.setUserPw(bCryptPasswordEncoder.encode(initialPassword));
                System.out.println("DEBUG: 초기 패스워드 설정됨 (생년월일): " + initialPassword);
            } else {
                System.err.println("ERROR: userBirthdate가 null이어서 초기 패스워드를 설정할 수 없습니다.");
                redirectAttributes.addFlashAttribute("message", "생년월일이 없어 초기 패스워드 설정에 실패했습니다.");
                return "redirect:/admin/create";
            }
            
            int result = dao.insert(accountDTO);
            System.out.println("### DEBUG: dao.insert() 결과: " + result + " ###"); // ★★★ 추가
            if (result == 1) {
                redirectAttributes.addFlashAttribute("message", "계정 생성에 성공했습니다. 초기 패스워드는 생년월일입니다.");
            } else {
                redirectAttributes.addFlashAttribute("message", "계정 생성에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "계정 생성 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/admin/create";
        }
        return "redirect:/admin/accountedit/list.do";
    }

    @PostMapping("/accountedit/toggleEnable.do")
    public String toggleEnable(@RequestParam("userId") String userId, @RequestParam("enable") int enable,
                               RedirectAttributes redirectAttributes) {

        try {
            int result = dao.updateEnableStatus(userId, enable);
            System.out.println("### DEBUG: dao.updateEnableStatus() 결과: " + result + " ###"); // ★★★ 추가
            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "회원 상태가 성공적으로 변경되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("message", "회원 상태 변경에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원 상태 변경 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/accountedit/list.do";
    }
}