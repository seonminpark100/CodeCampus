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
	/*
		인증 핸들러를 제작했다면 사용을 위해 빈을 자동주입 받는다. 
		그리고 시큐리티 설정 부분의 failureHandler() 메서드에 추가한다. 
	 */
	@Autowired
    public MyAuthFailureHandler myAuthFailureHandler;
	
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf((csrf)->csrf.disable()) 
			.cors((cors)-> cors.disable()) 
			.authorizeHttpRequests((request) -> request	// http 요청에 대한 인가 설정 처리
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() 
				.requestMatchers("/").permitAll() 
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/admin/create").permitAll() // ★★★ 추가: 계정 생성 페이지 허용 ★★★
				.requestMatchers("/user/**").hasAnyRole("USER", "PROF", "ADMIN")
				.requestMatchers("/prof/**").hasAnyRole("PROF", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
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
				.invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID")
				.permitAll());

		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));
		
		//http.sessionManagement((auth) -> auth
                //.maximumSessions(1) // 최대 다중 로그인 허용자 설정
                //.maxSessionsPreventsLogin(true)); 
		
		
		return http.build();
	}
	

	@Autowired
	private DataSource dataSource;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
			// 데이터베이스 접속 정보를 먼저 이용
			.dataSource(dataSource)
			// 쿼리로 해당 사용자가 있는지를 먼저 조회한다
			.usersByUsernameQuery("SELECT user_id, user_pw, enable "
					+ " FROM USER_INFO WHERE user_id = ?")
			// 사용자의 역할을 구해온다
			.authoritiesByUsernameQuery("SELECT user_id, authority "
					+ " FROM USER_INFO WHERE user_id =?")
			// 입력한 비밀번호를 암호화해서 데이터베이스의 암호와 비교를 해서 
			// 올바른 값인지 검증
			.passwordEncoder(new BCryptPasswordEncoder());
			// enabled 의 값이 0이면 비활성, 1이면 활성
	}
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
