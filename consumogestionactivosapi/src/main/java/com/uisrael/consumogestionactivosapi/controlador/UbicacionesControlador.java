package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ubicaciones") // url
public class UbicacionesControlador {
	
	@GetMapping
	public String listarUbicaciones() {
		return "ubicaciones/listarUbicaciones"; // ubicacion fisica page
	}

	@GetMapping("/nueva-ubicacion")
	public String nuevaUbicacion() {
		return "ubicaciones/nuevaUbicacion"; // ubicacion fisica page
	}
	
	@GetMapping("/editar-ubicacion")
	public String modificarUbicacion() {
		return "ubicaciones/editarUbicacion"; // ubicacion fisica page
	}

}
