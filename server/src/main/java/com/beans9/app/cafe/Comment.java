package com.beans9.app.cafe;

import java.time.LocalDateTime;

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
public class Comment {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String content;
	
	@OneToOne
	@JoinColumn(name="appUser_id")
	private AppUser appUser;
	
	@OneToOne
	@JoinColumn(name="targetUser_id")
	private AppUser targetUser;
	
	@JsonIgnore
	@ManyToOne
	private Cafe cafe;
	
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
}
