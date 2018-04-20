package com.beans9.app.security;

import static com.beans9.app.security.SecurityConstants.EXPIRATION_TIME;
import static com.beans9.app.security.SecurityConstants.SECRET;
import static com.beans9.app.security.SecurityConstants.TOKEN_PREFIX;
import static com.beans9.app.security.SecurityConstants.USER_EMAIL;
import static com.beans9.app.security.SecurityConstants.USER_ID;
import static com.beans9.app.security.SecurityConstants.USER_NAME;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecurityUtil {
	public static String getToken(String username, String email, long id) {
		String token = Jwts.builder().setSubject(username)
				.claim(USER_ID, String.valueOf(id))
				.claim(USER_EMAIL, String.valueOf(email))
				.claim(USER_NAME, String.valueOf(username))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();
		return TOKEN_PREFIX + token;
	}
}
