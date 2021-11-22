package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//	@GetMapping("/cliente/{id}")
//	public cliente devolverCliente(@PathVariable Long id) {
//
//		return clienteService.devolverCliente(id);
//
//	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> devolverCliente(@PathVariable Long id) {
		cliente c= null;
		Map<String,Object> response= new HashMap<>();
		try {
			c =clienteService.devolverCliente(id);
					
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		if(c==null) {
			response.put("mensaje", "el cliente id: ".concat(id.toString().concat(" no existe")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<cliente>(c,HttpStatus.OK);
		
		
	}
	
	
	
//	@PostMapping("/cliente")
//	public cliente guardar(@RequestBody cliente c) {
//		return clienteService.guardar(c);
//	}
	
	@PostMapping("/cliente")
	public ResponseEntity<?> guardar(@RequestBody cliente c) {
		cliente cnuevo= null;
		Map<String,Object> response= new HashMap<>();
		try {
			c =clienteService.guardar(c);
					
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "el cliente ha sido creado");
		response.put("cliente", cnuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
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
