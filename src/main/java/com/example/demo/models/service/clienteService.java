package com.example.demo.models.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.entity.cliente;
import com.example.demo.models.entity.region;


public interface clienteService {
	
	public List<cliente> findAll();
	
	public cliente devolverCliente(Long id);
	public List<region> findAllRegion();
	public cliente guardar(cliente c);
	public void borrar(Long id);
	

}
