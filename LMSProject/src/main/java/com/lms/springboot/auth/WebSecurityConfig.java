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
				.requestMatchers("/myLogin.do").permitAll() // 로그인 페이지 접근 허용
				.requestMatchers("/myLoginAction.do").permitAll() // 로그인 처리 URL 허용
				.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN", "INSTRUCTOR")	 // 세 가지 권한 허용 (INSTRUCTOR 추가)
				.requestMatchers("/admin/**").hasRole("ADMIN")	// ADMIN만 허용
				.anyRequest().authenticated() 	// 어떠한 요청이라도 인증 필요
			);

		http.formLogin((formLogin) -> formLogin
				.loginPage("/myLogin.do")		// default : /login
				.loginProcessingUrl("/myLoginAction.do")
//				.failureUrl("/myError.do") 		// default : /login?error
				.failureHandler(myAuthFailureHandler)
				.usernameParameter("my_id") 	// default : username
				.passwordParameter("my_pass")	// default : password
				.permitAll());

		http.logout((logout) -> logout			// default : /logout
				.logoutUrl("/myLogout.do")
				.logoutSuccessUrl("/")
				.permitAll());

		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));

		return http.build();
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			
			.usersByUsernameQuery("SELECT user_id, user_pw, enabled FROM security_admin WHERE user_id = ?")
            .authoritiesByUsernameQuery("SELECT user_id, authority FROM USER_INFO WHERE user_id =?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}