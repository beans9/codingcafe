package com.beans9.app.user;
import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class LoginUserDetails extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	private long id;
	
	public LoginUserDetails(AppUser user)
	{
		super
		(
			user.getUsername(),
			user.getPassword(),
			emptyList()
			
		);
		id = user.getId();
	}
}
