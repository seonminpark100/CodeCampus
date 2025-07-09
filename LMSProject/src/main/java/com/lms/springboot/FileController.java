package com.lms.springboot;

import com.lms.springboot.jdbc.AccountDTO; // AccountDTO 사용 (UserDTO 대신)
import com.lms.springboot.jdbc.IMemberService; // IMemberService 주입 (UserService 대신)
import com.lms.springboot.utils.MyFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final String UPLOAD_BASE_DIR = "C:/Devdata1111/CodeCampus/Uploadfile/";

    @Autowired
    @Qualifier("accountDAO")
    private IMemberService memberService; 

    public FileController() {
        // 애플리케이션 시작 시 업로드 디렉토리가 없으면 생성합니다.
        File uploadDir = new File(UPLOAD_BASE_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 하위 디렉토리도 함께 생성
            System.out.println("업로드 디렉토리 생성: " + UPLOAD_BASE_DIR);
        }
    }


    @GetMapping("/uploadProfile.do")
    public String uploadProfileForm(@RequestParam("userId") String userId, Model model) {
        // userId를 JSP(profileUpload.jsp)로 전달하여 hidden 필드에 사용될 수 있도록 합니다.
        model.addAttribute("userId", userId);
        return "user/profileUpload"; // WEB-INF/views/user/profileUpload.jsp를 렌더링
    }

  
    @PostMapping("/uploadProfileProcess.do")
    public String uploadProfileProcess(
            @RequestParam("userId") String userId,
            @RequestParam("ofile") MultipartFile ofile,
            RedirectAttributes redirectAttributes) {

    
        String uploadSubDir = UPLOAD_BASE_DIR + "user_profiles/"; 
        File subDir = new File(uploadSubDir);
        if (!subDir.exists()) {
            subDir.mkdirs(); 
        }

        String originalFileName = null; 
        String savedFileName = null;    

        try {
            // 1. 파일이 비어있는지 확인
            if (ofile.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "첨부할 파일이 없습니다.");
              
                return "redirect:/user/mypage.do?userId=" + userId; 
            }

            // 2. 서버 측 파일 크기 유효성 검사 (5MB 제한)
            final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
            if (ofile.getSize() > MAX_FILE_SIZE) {
                redirectAttributes.addFlashAttribute("message", "파일 크기가 너무 큽니다. (최대 5MB)");
                return "redirect:/user/mypage.do?userId=" + userId;
            }

            // 3. 파일 확장자 유효성 검사 (이미지 파일만 허용)
            originalFileName = ofile.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            }

            // 허용된 이미지 확장자 목록
            if (!fileExtension.matches("\\.(jpg|jpeg|png|gif)$")) {
                redirectAttributes.addFlashAttribute("message", "허용되지 않는 이미지 형식입니다. (허용: jpg, jpeg, png, gif)");
                return "redirect:/user/mypage.do?userId=" + userId;
            }


            AccountDTO currentUserInfo = new AccountDTO();
            currentUserInfo.setUserId(userId); // 조회할 사용자 ID 설정
            AccountDTO existingUser = memberService.selectOne(currentUserInfo); // DB에서 사용자 정보 조회

            if (existingUser != null && existingUser.getSavefile() != null && !existingUser.getSavefile().isEmpty()) {
                File oldFile = new File(uploadSubDir + existingUser.getSavefile());
                if (oldFile.exists()) {
                    oldFile.delete(); // 물리적 파일 삭제
                    System.out.println("기존 프로필 파일 삭제 완료: " + oldFile.getAbsolutePath());
                }
            }

            // 5. 새로운 파일 시스템에 저장 (고유한 이름으로 변경하여 저장)
            // MultipartFile을 임시 파일로 먼저 저장한 후, renameFile 함수를 통해 파일명 변경
            File tempFile = new File(uploadSubDir, originalFileName); // 먼저 원본 파일명으로 임시 저장
            ofile.transferTo(tempFile); // 실제 파일 데이터 저장

            savedFileName = MyFunctions.renameFile(uploadSubDir, originalFileName); // MyFunctions를 이용해 파일명 변경

            AccountDTO userToUpdate = new AccountDTO();
            userToUpdate.setUserId(userId);             // 어떤 사용자의 정보를 업데이트할지 설정
            userToUpdate.setSavefile(savedFileName);    // 서버에 저장된 파일명
            userToUpdate.setOriginalfile(originalFileName); // 사용자가 업로드한 원본 파일명


            int result = memberService.update(userToUpdate); 

            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "프로필 이미지가 성공적으로 업데이트되었습니다!");
                System.out.println("프로필 이미지 DB 업데이트 성공: userId=" + userId + ", savedFile=" + savedFileName);
            } else {
                // DB 업데이트 실패 시: 이미 저장된 물리적 파일을 롤백 (삭제)
                File savedFileOnDisk = new File(uploadSubDir + savedFileName);
                if (savedFileOnDisk.exists()) {
                    savedFileOnDisk.delete();
                    System.err.println("DB 업데이트 실패로 인해 저장된 파일 롤백(삭제): " + savedFileOnDisk.getAbsolutePath());
                }
                redirectAttributes.addFlashAttribute("message", "프로필 이미지 업데이트에 실패했습니다.");
                System.err.println("프로필 이미지 DB 업데이트 실패: userId=" + userId);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("파일 입출력 오류 발생: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("프로필 이미지 업로드 처리 중 예상치 못한 오류 발생: " + e.getMessage());
            // 예외 발생 시, 이미 저장된 파일이 있다면 삭제하여 롤백을 시도합니다.
            if (savedFileName != null) { // savedFileName이 유효할 때만 삭제 시도
                File savedFileOnDisk = new File(uploadSubDir + savedFileName);
                if (savedFileOnDisk.exists()) {
                    savedFileOnDisk.delete();
                    System.err.println("예외 발생으로 인해 저장된 파일 롤백(삭제): " + savedFileOnDisk.getAbsolutePath());
                }
            }
            redirectAttributes.addFlashAttribute("message", "프로필 이미지 업데이트 처리 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/user/mypage.do?userId=" + userId;
    }
}