package com.lms.springboot.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// 필요한 import 추가
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler
{
    // Spring Security가 로그인 전에 저장했던 요청을 가져오기 위한 객체
    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

	@Override
	 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			 Authentication authentication) throws IOException, ServletException {
        
        // 1. 로그인 전에 사용자가 접근하려고 했던 URL (SavedRequest)이 있는지 확인
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // SavedRequest가 있다면, 해당 URL로 리다이렉트 (로그인 전 요청 페이지로 돌아감)
            String targetUrl = savedRequest.getRedirectUrl();
            requestCache.removeRequest(request, response); // 세션에서 SavedRequest 제거 (선택 사항이지만 일반적으로 제거)
            response.sendRedirect(targetUrl);
            return; // 리다이렉트 후 메서드 종료
        }

        // 2. SavedRequest가 없다면 (예: 직접 로그인 페이지에 접근했거나, 접근하려던 페이지가 permitAll()이었을 경우)
        //    기존 로직대로 사용자 역할에 따른 기본 페이지로 리다이렉트
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        
        // 주의: 사용자가 여러 권한을 가질 경우, 이 로직은 첫 번째 권한만 사용하여 처리합니다.
        // 만약 더 복잡한 권한 기반 리다이렉트가 필요하면 로직을 개선해야 합니다.
        String role = iterator.next().getAuthority(); 

        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.equals("ROLE_PROF")) {
            response.sendRedirect("/prof/index.do");
        } else if (role.equals("ROLE_USER")){
            response.sendRedirect("/user/index.do");
        } else {
            // 예상치 못한 역할이거나, 기본적으로 갈 곳이 없다면 홈으로 리다이렉트
            response.sendRedirect("/");
        }
    }
}