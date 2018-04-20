package com.beans9.app.security;

import static com.beans9.app.security.SecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beans9.app.user.AppUser;
import com.beans9.app.user.LoginUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try {
			AppUser creds = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
		LoginUserDetails loginUser = (LoginUserDetails) auth.getPrincipal();
		String token = SecurityUtil.getToken(loginUser.getUsername(), loginUser.getEmail(), loginUser.getId());
		res.addHeader(HEADER_STRING, token);
		ServletOutputStream resOut = res.getOutputStream();
		
		HashMap<String,Object> userInfo = new HashMap<String,Object>();
		userInfo.put("token", token);
		userInfo.put("email", loginUser.getEmail());
		userInfo.put("username", loginUser.getUsername());
		userInfo.put("id", loginUser.getId());
		
		Gson gson = new Gson(); 
		String json = gson.toJson(userInfo);
		
		resOut.print(json);
		resOut.close();
	}
}
