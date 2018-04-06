package com.beans9.app.cafe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cafe {
	@Id @GeneratedValue
	private long id;	
	private String name;
}
