package com.lms.springboot.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler
{

	@Override
	 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			 Authentication authentication) throws IOException, ServletException {
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
	
}
