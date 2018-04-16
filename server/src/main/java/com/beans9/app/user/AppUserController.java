package com.beans9.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@ResponseBody
	@GetMapping
	public AppUser getUserInfo(@AuthenticationPrincipal LoginUserDetails userDetails) {
		return appUserRepo.findById(userDetails.getId()).get();
	}
	
	@PostMapping("/sign-up")
	public AppUser signUp(@RequestBody AppUser appUser) {
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepo.save(appUser);
		appUser.setToken(securityUtil.getToken(appUser.getUsername(), appUser.getId()));
		return appUser;
	}
}
