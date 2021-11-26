package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			cnuevo=c;	
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "el cliente ha sido creado");
		response.put("cliente", cnuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
//	@PutMapping("/cliente/{id}")
//	public cliente actualizar(@RequestBody cliente c, @PathVariable Long id) {
//		cliente cact = clienteService.devolverCliente(id);
//		cact.setApellido(c.getApellido());
//		cact.setNombre(c.getNombre());
//		cact.setEmail(c.getEmail());
//		cact.setTelefono(c.getTelefono());
//		return clienteService.guardar(cact);
//	}
	
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> actualizar(@RequestBody cliente c,@PathVariable Long id) {
		cliente cActual= clienteService.devolverCliente(id);
		Map<String,Object> response= new HashMap<>();
		
		if(cActual==null) {
			response.put("mensaje", "el cliente id: ".concat(id.toString().concat(" no existe")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			cActual.setNombre(c.getNombre());
			cActual.setApellido(c.getApellido());
			cActual.setEmail(c.getEmail());
			cActual.setTelefono(c.getTelefono());
			cActual.setCreatedAt(c.getCreatedAt());
			
			clienteService.guardar(cActual);
					
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "el cliente ha sido modificado");
		response.put("cliente", cActual);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
//	@DeleteMapping("/cliente/{id}")
//	public void borrar(@PathVariable Long id) {
//		clienteService.borrar(id);
//
//	}
	
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String,Object> response= new HashMap<>();
		try {
			clienteService.borrar(id);
					
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "el cliente ha sido eliminado");
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	

	}
	
	@PostMapping("cliente/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,@RequestParam("id") Long id){
		Map<String,Object> response= new HashMap<>();
		cliente c=clienteService.devolverCliente(id);
		if(!archivo.isEmpty()) {
			String nombreArchivo=UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo=Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			
			if(c.getImagen()!=null) {
				String n=c.getImagen();
				Path rutaAntigua=Paths.get("uploads").resolve(n).toAbsolutePath();
				File archivoAntiguo=rutaAntigua.toFile();
				archivoAntiguo.delete();
			}
				
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			}catch(IOException e){
				response.put("mensaje", "Error al subir la imagen");
				response.put("error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			c.setImagen(nombreArchivo);
			clienteService.guardar(c);
			response.put("cliente", c);
			response.put("mensaje", " la imagen "+ nombreArchivo+ " se ha subido correctamente");
			
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Path rutaArchivo =Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource r=null;
		try {
			r= new UrlResource(rutaArchivo.toUri());
		}catch(MalformedURLException e){
			e.printStackTrace();
			
		}
		if(!r.exists()&&!r.isReadable()) {
			throw new RuntimeException("No se puede cargar la imagen "+ nombreFoto);
		}
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_DISPOSITION,"attachement;filename=\""+r.getFilename()+"\"");
		return new ResponseEntity<Resource>(r,h,HttpStatus.OK);
		
	}
	
	

}
