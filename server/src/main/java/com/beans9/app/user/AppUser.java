package com.beans9.app.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AppUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String token;
	
	public AppUser() {}
	public AppUser(long id) {
		this.id = id;
	}
}
