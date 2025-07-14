package com.lms.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig
{
	@Autowired
    public MyAuthFailureHandler myAuthFailureHandler;
	
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf((csrf)->csrf.disable()) 
			.cors((cors)-> cors.disable()) 
			.authorizeHttpRequests((request) -> request	// http 요청에 대한 인가 설정 처리
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() 
				.requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
				// GET 요청은 모두 허용합니다. (조회)
				.requestMatchers(org.springframework.http.HttpMethod.GET, 
                                 "/noticeboard/noticelist.do", 
                                 "/noticeboard/noticeview.do", 
                                 "/noticeboard/api/latestNotices",
                                 "/noticeboard/noticeedit.do", // 수정 폼 접근도 GET이므로 허용 (조회 목적)
                                 "/noticeboard/noticewrite.do" // 작성 폼 접근도 GET이므로 허용
                                ).permitAll()
				// POST 요청 (수정, 삭제, 작성)은 ADMIN 권한만 허용합니다.
				.requestMatchers(org.springframework.http.HttpMethod.POST, 
                                 "/noticeboard/noticeedit.do", 
                                 "/noticeboard/noticedelete.do",
                                 "/noticeboard/noticewrite.do"
                                ).hasRole("ADMIN") // ADMIN 권한만 허용
				.requestMatchers("/user/**").hasAnyRole("USER", "PROF", "ADMIN")
				.requestMatchers("/prof/**").hasAnyRole("PROF", "ADMIN")	 // 두권한 허용
				.requestMatchers("/admin/**").hasRole("ADMIN")	// ADMIN만 허용
				
				.requestMatchers("/qnaboard/qnaboardlist.do", "/qnaboard/qnaboardview.do").permitAll() 
	            // Q&A 작성 폼 접근 (GET)은 인증만 되면 허용
	            .requestMatchers(org.springframework.http.HttpMethod.GET, "/qnaboard/qnaboardwrite.do").authenticated() 
	            // Q&A 작성 (POST), 수정 (POST), 삭제 (POST)
	            .requestMatchers(org.springframework.http.HttpMethod.POST,
	                             "/qnaboard/qnaboardwrite.do",
	                             "/qnaboard/qnaboardedit.do",
	                             "/qnaboard/qnadelete.do"
	                            ).hasAnyRole("USER", "PROF", "ADMIN") 
	           
	            .requestMatchers("/qnaboard/qnanswer.do").hasAnyRole("PROF", "ADMIN") // 답변은 교수, 관리자만 (GET/POST 모두 적용)

				.anyRequest().authenticated() 
			);

		http.formLogin((formLogin) -> formLogin
				.loginPage("/myLogin.do")		// default : /login
				.loginProcessingUrl("/myLoginAction.do")
				.successHandler(myAuthSuccessHandler)
//				.failureUrl("/myError.do") 		// default : /login?error
				.failureHandler(myAuthFailureHandler)
				.usernameParameter("my_id") 	// default : username
				.passwordParameter("my_pass")	// default : password
				.permitAll());
		
		http.logout((logout) -> logout			// default : /logout
				.logoutUrl("/myLogout.do")
				.logoutSuccessUrl("/")
				
//				재로그인을 위한 설정
				.invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
				
				.permitAll());
		

		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));
		
//		http.sessionManagement((auth) -> auth
//                .maximumSessions(1) // 최대 다중 로그인 허용자 설정
//                .maxSessionsPreventsLogin(true)); 
		
		return http.build();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT user_id, user_pw, enable "
					+ " FROM USER_INFO WHERE user_id = ?")
			.authoritiesByUsernameQuery("SELECT user_id, authority "
					+ " FROM USER_INFO WHERE user_id =?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
	}
	
}