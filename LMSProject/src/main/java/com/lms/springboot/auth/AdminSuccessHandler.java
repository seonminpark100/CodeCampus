package com.lms.springboot.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class AdminSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/"; // 기본 리다이렉트 URL

        boolean isAdmin = false;
        boolean isLecturer = false;
        // 다른 역할도 필요하다면 여기에 추가

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority(); // 예: "ROLE_USER", "ROLE_LECTURER", "ROLE_ADMIN"

            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
                break; // ADMIN이 최우선이므로 찾으면 종료
            } else if ("ROLE_LECTURER".equals(role)) {
                isLecturer = true; // 강사 권한을 확인
            }
            // 다른 역할에 대한 조건도 여기에 추가할 수 있습니다.
        }

        if (isAdmin) {
            redirectUrl = "/admin/dashboard"; // 관리자 대시보드 URL
        } else if (isLecturer) {
            redirectUrl = "/lecturer/dashboard"; // 강사 대시보드 URL
        } else {
            redirectUrl = "/member/dashboard"; // 일반 사용자 대시보드 URL (또는 홈 `/`)
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}