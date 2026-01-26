package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedores") // url
public class ProveedoresControlador {

	@GetMapping
	public String listarProveedores() {
		return "proveedores/listarProveedores"; // ubicacion fisica page
	}

	@GetMapping("/nuevoProveedores")
	public String nuevoProveedores() {
		return "proveedores/nuevoProveedores"; // ubicacion fisica page
	}
	
	@GetMapping("/editarProveedores")
	public String modificarProveedores() {
		return "proveedores/editarProveedores"; // ubicacion fisica page
	}

}