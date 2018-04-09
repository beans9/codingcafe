package com.beans9.app.user;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<AppUser, Long>{
	public AppUser findByUsername(String username);
}
