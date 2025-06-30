package com.lms.springboot.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // AuthenticationManager 임포트
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // DaoAuthenticationProvider 임포트
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // AuthenticationConfiguration 임포트
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // 로그아웃 URL 설정 시 필요

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    public MyAuthFailureHandler myAuthFailureHandler;

    @Autowired
    public AdminSuccessHandler adminSuccessHandler;

    // MyUserDetailsService 빈 주입 (이것은 올바르게 되어 있습니다)
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    // BCryptPasswordEncoder 빈 정의 (이것은 올바르게 되어 있습니다)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- 여기부터 중요: DaoAuthenticationProvider 빈을 추가해야 합니다 ---
    // 이 빈이 MyUserDetailsService와 passwordEncoder를 사용하여 인증을 처리합니다.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService); // MyUserDetailsService 설정
        authProvider.setPasswordEncoder(passwordEncoder()); // BCryptPasswordEncoder 설정
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // --- 여기까지 중요 ---

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발 편의상)
                .cors(cors -> cors.disable()) // CORS 비활성화 (개발 편의상)
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/myLogin.do", "/myLoginAction.do", "/myLogout.do", "/register", "/denied.do").permitAll()
                        .requestMatchers("/member/**").hasAnyRole("USER", "LECTURER", "ADMIN")
                        .requestMatchers("/lecturer/**").hasAnyRole("LECTURER", "ADMIN") // 강사 전용 페이지
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한만 필요
                       // .anyRequest().authenticated())
                		.anyRequest().anonymous())
                .formLogin(formLogin -> formLogin
                        .loginPage("/myLogin.do") // 로그인 페이지 URL
                        .loginProcessingUrl("/myLoginAction.do") // 로그인 처리 URL
                        .successHandler(adminSuccessHandler) // 커스텀 성공 핸들러 사용
                        .failureHandler(myAuthFailureHandler) // 로그인 실패 핸들러
                        .usernameParameter("my_id") // 로그인 폼의 아이디 필드 name 속성
                        .passwordParameter("my_pass") // 로그인 폼의 비밀번호 필드 name 속성
                        .permitAll()) // 로그인 관련 설정은 모두 허용
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/myLogout.do")) // 로그아웃 요청 URL (AntPathRequestMatcher 사용 권장)
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트될 URL
                        .permitAll())
                .exceptionHandling(expHandling -> expHandling
                        .accessDeniedPage("/denied.do") // 접근 거부 페이지
                );
        return http.build();
    }
}