package com.beans9.app.cafe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.beans9.app.user.AppUser;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cafe {
	@Id @GeneratedValue
	private long id;	
	private String name;
	private String memo;
	

	@OneToOne
	@JoinColumn(name="USER_ID")
	private AppUser appUser;
}