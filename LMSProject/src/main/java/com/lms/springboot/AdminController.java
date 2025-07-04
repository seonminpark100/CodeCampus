package com.lms.springboot;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.springboot.jdbc.AccountDTO;
import com.lms.springboot.jdbc.IMemberService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IMemberService dao;

    public AdminController(BCryptPasswordEncoder bCryptPasswordEncoder) { 
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/accountedit/list.do") 
    public String member2(Model model,
                          @RequestParam(value = "searchField", required = false) String searchField,
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

    @GetMapping("/accountedit/edit.do") 
    public String member3(AccountDTO accountDTO, Model model) {
        accountDTO = dao.selectOne(accountDTO);
        model.addAttribute("dto", accountDTO);
        return "accountedit/edit"; 
    }

    @PostMapping("/accountedit/edit.do") 
    public String member7(@ModelAttribute AccountDTO accountDTO, RedirectAttributes redirectAttributes) {

        System.out.println("DEBUG: accountDTO.getUserGender(): " + accountDTO.getUserGender());

        AccountDTO existingAccount = dao.selectOne(accountDTO);
        if (existingAccount == null) {
            redirectAttributes.addFlashAttribute("message", "회원 정보를 찾을 수 없어 수정에 실패했습니다.");
            return "redirect:/admin/accountedit/list.do"; // ★★★ 리다이렉트 경로 수정 (클래스 레벨 @RequestMapping 고려)
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
        return "admin/create"; 
    }

    
    @PostMapping("/create") 
    public String createAccount(@ModelAttribute AccountDTO accountDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            // 파일 업로드 관련 로직 제거
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

            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "회원 상태가 성공적으로 변경되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("message", "회원 상태 변경에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원 상태 변경 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/accountedit/list.do"; // ★★★ 리다이렉트 경로 수정 (클래스 레벨 @RequestMapping 고려)
    }
}