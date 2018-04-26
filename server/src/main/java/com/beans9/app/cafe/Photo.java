package com.beans9.app.cafe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Photo {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;	
	private String name;
	private String realName;
	private long size;
	private boolean isDefault;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="cafe_id")
	private Cafe cafe;
	
	public Photo() {}
	public Photo(String name, String realName, long size, Cafe cafe) {
		this.name = name;
		this.realName = realName;
		this.size = size;
		this.cafe = cafe;
	}
}
