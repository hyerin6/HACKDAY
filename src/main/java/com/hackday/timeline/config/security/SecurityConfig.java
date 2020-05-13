package com.hackday.timeline.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.hackday.timeline.common.security.CustomAccessDeniedHandler;
import com.hackday.timeline.common.security.CustomLoginSuccessHandler;
import com.hackday.timeline.common.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception {


		http.formLogin()
			.loginPage("/auth/login")
			.loginProcessingUrl("/login")
			//CustomLoginSuccesshandler를 로그인 성공 처리자로 지정
			.successHandler(createAuthenticationSuccessHandler());

		http.logout()
			.logoutUrl("/auth/logout")
			.invalidateHttpSession(true)
			//로그아웃 시 상태유지 쿠키 삭제
			.deleteCookies("remember-me", "JSESSION_ID");

		http.exceptionHandling()
			//CustomAccessDeniedHandler를 접근 거부 처리자로 지정
			.accessDeniedHandler(createAccessDeniedHandler());

		//데이터 소스를 지정하고 테이블을 이용하여 기존 로그인 정보를 기록
		http.rememberMe()
			.key("daehwan")
			.tokenRepository(createJDBCRepository())
			//쿠기 유효시간 지정
			.tokenValiditySeconds(60 * 60 * 24);
	}

	//CustomUserDetailsService 빈의 인증 제공자에 지정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(createUserDetailsService())
			.passwordEncoder(createPasswordEncoder());
	}

	//빈 정의
	@Bean
	public UserDetailsService createUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public AuthenticationSuccessHandler createAuthenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler createAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private PersistentTokenRepository createJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);

		return repo;
	}
}
