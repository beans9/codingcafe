package com.beans9.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beans9.app.security.SecurityUtil;

@RestController
@RequestMapping("/users")
public class AppUserController {
	private AppUserRepo appUserRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SecurityUtil securityUtil;

	@Autowired
	public AppUserController(AppUserRepo appUserRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepo = appUserRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping("/sign-up")
	public AppUser signUp(@RequestBody AppUser appUser) {
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepo.save(appUser);
		appUser.setToken(securityUtil.getToken(appUser.getUsername(), appUser.getId()));
		return appUser;
	}
}
