package com.example.demo.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

	public Usuario findUsuario(String username);
	
	@Query("select u from Usuario u where u.username=?")
	public Usuario findByUsuario(String username);
	
	
}
