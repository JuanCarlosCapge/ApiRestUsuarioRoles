package com.example.demo.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.cliente;
import com.example.demo.models.entity.region;

public interface clienteDao extends CrudRepository<cliente, Long>{
	
	
	@Query("from region")
	public List<region> finAllRegion();
}
