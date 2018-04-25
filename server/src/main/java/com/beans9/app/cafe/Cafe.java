package com.beans9.app.cafe;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.beans9.app.user.AppUser;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cafe {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;	
	private String name;
	private String memo;

	@OneToOne
	@JoinColumn(name="appUser_id")
	private AppUser appUser;
	
	@OneToMany
	private List<Photo> photos;
}