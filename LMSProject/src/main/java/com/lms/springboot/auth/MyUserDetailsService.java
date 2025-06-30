package com.lms.springboot.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.springboot.jdbc.IMemberService;
import com.lms.springboot.jdbc.MemberDTO;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IMemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loadUserByUsername 메소드의 파라미터인 'username'을 사용하여 사용자 정보를 조회합니다.
        MemberDTO member = memberService.selectOne(username);

        if (member == null) {
            System.out.println("DB에서 사용자 " + username + "를 찾을 수 없습니다.");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String rawAuthority = member.getAuthority(); // 데이터베이스에서 가져온 그대로의 권한 문자열

        if (rawAuthority != null && !rawAuthority.isEmpty()) {
            
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rawAuthority.toUpperCase())); // 대문자 변환 추가
        } else {
            
            throw new InternalAuthenticationServiceException("User " + username + " has no authority defined.");
        }

       
        if (member.getUserPw() == null || member.getUserPw().isEmpty()) {
            throw new InternalAuthenticationServiceException("User " + username + " has no password defined.");
        }

        return new User(member.getUserId(), member.getUserPw(), authorities);
    }
}