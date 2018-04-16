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
	    
	public LoginUserDetails(AppUser user)
	{
		super
		(
			user.getUsername(),
			user.getPassword(),
			emptyList()
			
		);
		this.id = user.getId();
	}
	
	public LoginUserDetails(String username, long userId) {
		super(
			username, 
			"", 
			emptyList()
		);
		this.id = userId;
	}
}
