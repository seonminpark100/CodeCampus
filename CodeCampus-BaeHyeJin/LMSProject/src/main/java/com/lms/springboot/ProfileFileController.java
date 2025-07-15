package com.lms.springboot;

import com.lms.springboot.jdbc.AccountDTO;
import com.lms.springboot.jdbc.IAccountService;
import com.lms.springboot.utils.MyFunctions; // 이 클래스가 파일명 변경 로직을 포함하는지 확인 필요

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID; 

@Controller
@RequestMapping("/file")
public class ProfileFileController {

    @Value("${file.upload-dir}")
    private String uploadBaseDir;

    private final IAccountService iaccountService;

    public ProfileFileController(@Value("${file.upload-dir}") String uploadBaseDir,
		            @Qualifier("accountDAO") IAccountService iaccountService) {
		this.uploadBaseDir = uploadBaseDir;
		this.iaccountService = iaccountService;
		File uploadDir = new File(this.uploadBaseDir);
		if (!uploadDir.exists()) {
		uploadDir.mkdirs();
		System.out.println("업로드 디렉토리 생성: " + this.uploadBaseDir);
		}
    }

    @GetMapping("/uploadProfile.do")
    public String uploadProfileForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/profileUpload"; 
    }

    @PostMapping("/uploadProfileProcess.do")
    public String uploadProfileProcess(
            @RequestParam("userId") String userId,
            @RequestParam("ofile") MultipartFile ofile,
            RedirectAttributes redirectAttributes) {

        String uploadSubDir = uploadBaseDir + "user_profiles/"; 
        File subDir = new File(uploadSubDir);
        if (!subDir.exists()) {
            subDir.mkdirs();
        }

        String originalFileName = null;
        String savedFileName = null;

        try {
            if (ofile.isEmpty()) {
                return "redirect:/admin/accountedit/edit.do?userId=" + userId + "&message=첨부할 파일이 없습니다.";
            }

            final long MAX_FILE_SIZE = 5 * 1024 * 1024;
            if (ofile.getSize() > MAX_FILE_SIZE) {
                 return "redirect:/admin/accountedit/edit.do?userId=" + userId + "&message=파일 크기가 너무 큽니다.(최대 5MB)";
            }

            originalFileName = ofile.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            }
            if (!fileExtension.matches("\\.(jpg|jpeg|png|gif)$")) {
                return "redirect:/admin/accountedit/edit.do?userId=" + userId + "&message=허용되지 않는 이미지 형식입니다.(허용: jpg, jpeg, png, gif)";
            }

            // 4. 기존 프로필 파일 삭제
            AccountDTO currentUserInfo = new AccountDTO();
            currentUserInfo.setUserId(userId);
            AccountDTO existingUser = iaccountService.selectOne(currentUserInfo);

            if (existingUser != null && existingUser.getSavefile() != null && !existingUser.getSavefile().isEmpty()) {
                Path oldFilePath = Paths.get(uploadSubDir, existingUser.getSavefile());
                try {
                    Files.deleteIfExists(oldFilePath);
                    System.out.println("기존 프로필 파일 삭제 완료: " + oldFilePath.toAbsolutePath());
                } catch (IOException e) {
                    System.err.println("기존 프로필 파일 삭제 실패 (물리적): " + oldFilePath.toAbsolutePath() + " - " + e.getMessage());
                }
            }

          
            String uuid = UUID.randomUUID().toString();
            savedFileName = uuid + fileExtension; // UUID + 원본 확장자
            Path destPath = Paths.get(uploadSubDir, savedFileName); // Path 객체 생성
            ofile.transferTo(destPath); // 파일 저장

            System.out.println("새 프로필 파일 저장 완료: " + destPath.toAbsolutePath());

            // 6. DB에 파일 정보 업데이트 (IMemberService의 전용 메서드 호출)
            int result = iaccountService.updateProfileImage(userId, savedFileName, originalFileName);

            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "프로필 이미지가 성공적으로 업데이트되었습니다!");
                System.out.println("프로필 이미지 DB 업데이트 성공: userId=" + userId + ", savedFile=" + savedFileName);
            } else {
                // DB 업데이트 실패 시: 이미 저장된 물리적 파일을 롤백 (삭제)
                try {
                    Files.deleteIfExists(destPath);
                    System.err.println("DB 업데이트 실패로 인해 저장된 파일 롤백(삭제): " + destPath.toAbsolutePath());
                } catch (IOException e) {
                    System.err.println("DB 롤백 후 파일 삭제 실패: " + destPath.toAbsolutePath() + " - " + e.getMessage());
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
            // 예외 발생 시, 이미 저장된 파일이 있다면 삭제하여 롤백을 시도
            if (savedFileName != null) { // savedFileName이 유효할 때만 삭제 시도
                Path savedFileOnDiskPath = Paths.get(uploadSubDir, savedFileName);
                try {
                    Files.deleteIfExists(savedFileOnDiskPath);
                    System.err.println("예외 발생으로 인해 저장된 파일 롤백(삭제): " + savedFileOnDiskPath.toAbsolutePath());
                } catch (IOException ioException) {
                    System.err.println("예외 발생 후 파일 삭제 실패: " + savedFileOnDiskPath.toAbsolutePath() + " - " + ioException.getMessage());
                }
            }
            redirectAttributes.addFlashAttribute("message", "프로필 이미지 업데이트 처리 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/admin/accountedit/edit.do?userId=" + userId;
    }
}