package com.beans9.app.security;
import static com.beans9.app.security.SecurityConstants.SIGN_UP_URL;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity (UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
			// 읽기는 허용
			.antMatchers("/h2console/**").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers(HttpMethod.GET, "/cafe/**").permitAll()
			.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().frameOptions().disable();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
}
