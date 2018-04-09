package com.beans9.app.user;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private AppUserRepo appUserRepo;
	
	public UserDetailsServiceImpl(AppUserRepo appUserRepo) {
		this.appUserRepo = appUserRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepo.findByUsername(username);
		if (appUser == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(appUser.getUsername(), appUser.getPassword(), emptyList());
	}
}
