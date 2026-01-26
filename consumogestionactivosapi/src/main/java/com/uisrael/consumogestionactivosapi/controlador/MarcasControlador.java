package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/marcas") // url
public class MarcasControlador {

	@GetMapping
	public String listarMarcas() {
		return "marcas/listarMarcas"; // ubicacion fisica page
	}

	@GetMapping("/nuevaMarcas")
	public String nuevaMarcas() {
		return "marcas/nuevaMarcas"; // ubicacion fisica page
	}
	
	@GetMapping("/editarMarcas")
	public String modificarMarcas() {
		return "marcas/editarMarcas"; // ubicacion fisica page
	}

}
