package com.example.demo.models.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private boolean enabled;
	private String password;
	private String usermane;
	
	
	
	public long getId() {
		return id;
	}



	public boolean isEnaibol() {
		return enabled;
	}



	public String getPassword() {
		return password;
	}



	public String getUsermane() {
		return usermane;
	}


	@ManyToMany
	private Set<Roles> roles;
	
}
