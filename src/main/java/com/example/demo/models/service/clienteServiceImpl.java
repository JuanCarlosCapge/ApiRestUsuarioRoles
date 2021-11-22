package com.example.demo.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.dao.clienteDao;
import com.example.demo.models.entity.cliente;

@Service
public class clienteServiceImpl implements clienteService{
	
	@Autowired
	private clienteDao clienteDao;
	
	@Override
	@Transactional (readOnly=true)
	public List<cliente> findAll() {
		return (List<cliente>) clienteDao.findAll();

	}
	@Override
	@Transactional(readOnly = true)
	public cliente devolverCliente(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public cliente  guardar(cliente c) {
		return clienteDao.save(c);
	}
	@Override
	@Transactional
	public void borrar(Long id) {
		clienteDao.deleteById(id);
	}
	




	
}
