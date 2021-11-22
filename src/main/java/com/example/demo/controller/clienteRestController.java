package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.entity.cliente;
import com.example.demo.models.service.clienteService;

@RestController
@RequestMapping("/api")
public class clienteRestController {
	
	@Autowired
	private clienteService clienteService;
	
	@GetMapping("/listaClientes")
	public List<cliente> index(){
		return clienteService.findAll();
	}
	@GetMapping("/cliente/{id}")
	public cliente devolverCliente(@PathVariable Long id) {
	
		return clienteService.devolverCliente(id);
		
	}
	
	@PostMapping("/cliente")
	public cliente guardar(@RequestBody cliente c) {
		return clienteService.guardar(c);
	}
	
	@PutMapping("/cliente/{id}")
		public cliente actualizar(@RequestBody cliente c, @PathVariable Long id) {
		cliente cact=clienteService.devolverCliente(id);
		cact.setApellido(c.getApellido());
		cact.setNombre(c.getNombre());
		cact.setEmail(c.getEmail());
		cact.setTelefono(c.getTelefono());
		return clienteService.guardar(cact);
		}
	
	@DeleteMapping("/cliente/{id}")
	public void borrar(@PathVariable Long id) {
		clienteService.borrar(id);

	}
	
	
	

}
