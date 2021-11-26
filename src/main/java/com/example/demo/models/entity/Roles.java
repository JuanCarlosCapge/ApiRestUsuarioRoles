package com.example.demo.models.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name= "roles")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nombre;
	
	@ManyToMany(mappedBy = "roles")
	private Set<Usuario> usuario;

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	
	
	
	
}
