package com.lms.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig
{
	/*
		인증 핸들러를 제작했다면 사용을 위해 빈을 자동주입 받는다. 
		그리고 시큐리티 설정 부분의 failureHandler() 메서드에 추가한다. 
	 */
	@Autowired
    public MyAuthFailureHandler myAuthFailureHandler;
	
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;

	@Bean
<<<<<<< HEAD
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
=======
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
>>>>>>> origin/master
		http.csrf((csrf)->csrf.disable()) 
			.cors((cors)-> cors.disable()) 
			.authorizeHttpRequests((request) -> request	// http 요청에 대한 인가 설정 처리
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() 
<<<<<<< HEAD
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
=======
				.requestMatchers("/").permitAll() 
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
>>>>>>> origin/master
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
	
<<<<<<< HEAD
	@Autowired
	private DataSource dataSource;
	
=======
	 /*
	    2단계(디자인 커스텀)에서 인메모리 방식으로 사용했던 메서드는 이번 단계에서는
	    사용하지 않으니 삭제처리한다. 
	 */
	
	//DB연결을 위한 데이터소스를 자동주입 받는다.
	@Autowired
	private DataSource dataSource;
	
	/*
	    아래 2개의 쿼리문 실행을 통해 사용자의 인증정보와 권한을 인출한다. 
	    첫번째 쿼리는 사용자의 아이디, 비번 그리고 계정활성화 여부를 확인한다. 
	    두번째 쿼리는 사용자의 권한(회원등급)을 확인한다. 
	 */
>>>>>>> origin/master
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
			// 데이터베이스 접속 정보를 먼저 이용
			.dataSource(dataSource)
<<<<<<< HEAD
			.usersByUsernameQuery("SELECT user_id, user_pw, enable "
					+ " FROM USER_INFO WHERE user_id = ?")
			.authoritiesByUsernameQuery("SELECT user_id, authority "
					+ " FROM USER_INFO WHERE user_id =?")
=======
			// 쿼리로 해당 사용자가 있는지를 먼저 조회한다
			.usersByUsernameQuery("SELECT user_id, user_pw, enable "
					+ " FROM USER_INFO WHERE user_id = ?")
			// 사용자의 역할을 구해온다
			.authoritiesByUsernameQuery("SELECT user_id, authority "
					+ " FROM USER_INFO WHERE user_id =?")
			// 입력한 비밀번호를 암호화해서 데이터베이스의 암호와 비교를 해서 
			// 올바른 값인지 검증
>>>>>>> origin/master
			.passwordEncoder(new BCryptPasswordEncoder());
			// enabled 의 값이 0이면 비활성, 1이면 활성
	}
<<<<<<< HEAD
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
	}
	
}
=======
}
>>>>>>> origin/master
