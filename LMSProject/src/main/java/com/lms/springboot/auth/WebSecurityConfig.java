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
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf((csrf)->csrf.disable()) 
			.cors((cors)-> cors.disable()) 
			.authorizeHttpRequests((request) -> request	// http 요청에 대한 인가 설정 처리
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() 
				.requestMatchers("/").permitAll() 
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/guest/**").permitAll()	// 모두에게 허용.
				.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")	 // 두권한 허용
				.requestMatchers("/admin/**").hasRole("ADMIN")	// ADMIN만 허용
//				.anyRequest().authenticated() 	// 어떠한 요청이라도 인증 필요
				.anyRequest().anonymous()
			);

		http.formLogin((formLogin) -> formLogin
				.loginPage("/loginPage.do")		// default : /login
				.loginProcessingUrl("/loginAction.do")
//				.failureUrl("/myError.do") 		// default : /login?error
				.failureHandler(myAuthFailureHandler)
				.usernameParameter("my_id") 	// default : username
				.passwordParameter("my_pass")	// default : password
				.permitAll());
		
		http.logout((logout) -> logout			// default : /logout
				.logoutUrl("/logout.do")
				.logoutSuccessUrl("/")
				.permitAll());

		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));
		
		return http.build();
	}
	
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
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
			// 데이터베이스 접속 정보를 먼저 이용
			.dataSource(dataSource)
			// 쿼리로 해당 사용자가 있는지를 먼저 조회한다
			.usersByUsernameQuery("SELECT user_id, user_pw, enabled "
					+ " FROM security_admin WHERE user_id = ?")
			// 사용자의 역할을 구해온다
			.authoritiesByUsernameQuery("SELECT user_id, authority "
					+ " FROM security_admin WHERE user_id =?")
			// 입력한 비밀번호를 암호화해서 데이터베이스의 암호와 비교를 해서 
			// 올바른 값인지 검증
			.passwordEncoder(new BCryptPasswordEncoder());
			// enabled 의 값이 0이면 비활성, 1이면 활성
	}
}
