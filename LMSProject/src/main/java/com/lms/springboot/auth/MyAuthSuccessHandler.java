package com.lms.springboot.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
// 필요한 import 추가
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

=======
>>>>>>> origin/master
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler
{
<<<<<<< HEAD
    // Spring Security가 로그인 전에 저장했던 요청을 가져오기 위한 객체
    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
=======
>>>>>>> origin/master

	@Override
	 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			 Authentication authentication) throws IOException, ServletException {
<<<<<<< HEAD
        
        // 1. 로그인 전에 사용자가 접근하려고 했던 URL (SavedRequest)이 있는지 확인
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // SavedRequest가 있다면, 해당 URL로 리다이렉트 (로그인 전 요청 페이지로 돌아감)
            String targetUrl = savedRequest.getRedirectUrl();
            requestCache.removeRequest(request, response); // 세션에서 SavedRequest 제거 (선택 사항이지만 일반적으로 제거)
            response.sendRedirect(targetUrl);
            return; // 리다이렉트 후 메서드 종료
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        

        String role = iterator.next().getAuthority(); 

        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.equals("ROLE_PROF")) {
            response.sendRedirect("/prof/index.do");
        } else if (role.equals("ROLE_USER")){
            response.sendRedirect("/");
        } else {
            
            response.sendRedirect("/");
        }
    }
}
=======
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = iterator.next().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/index.do"); // Admin 권한을 가진 사용자는 /admin/index.do 로 이동
        } else if (role.equals("ROLE_PROF")) {
            response.sendRedirect("/prof/index.do"); // Prof 권한을 가진 사용자는 /prof/index.do 로 이동
        } else if (role.equals("ROLE_USER")){
            response.sendRedirect("/user/index.do"); // USER 권한을 가진 사용자는 /user/index.do로 이동
        }
    }
	
<<<<<<< HEAD
}
=======
}
>>>>>>> master
>>>>>>> origin/master
