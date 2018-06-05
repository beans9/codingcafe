package com.beans9.app.cafe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.beans9.app.user.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="appUser_id")
	private AppUser appUser;
	
	@JsonIgnore
	@ManyToOne
	private Cafe cafe;
	
	Tag(){}
	Tag(String name, AppUser user, Cafe cafe){
		this.name = name;
		this.appUser = user;
		this.cafe = cafe;
	}
}
