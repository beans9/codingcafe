package com.beans9.app.user;
import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDetails extends User{
	private static final long serialVersionUID = 1L;
	private long id;
	private String email;
	    
	public LoginUserDetails(AppUser user)
	{
		super
		(
			user.getUsername(),
			user.getPassword(),
			emptyList()
			
		);
		this.id = user.getId();
		this.email = user.getEmail();
	}
	
	public LoginUserDetails(String username, String email, long userId) {
		super(
			username, 
			"", 
			emptyList()
		);
		this.id = userId;
		this.email = email;
	}
}
