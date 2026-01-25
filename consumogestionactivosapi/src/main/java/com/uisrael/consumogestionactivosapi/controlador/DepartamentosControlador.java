package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/departamentos") // url
public class DepartamentosControlador {

	@GetMapping
	public String listarDepartamentos() {
		return "departamentos/listarDepartamentos"; // ubicacion fisica page
	}

	@GetMapping("/nuevo-departamento")
	public String nuevoDepartamento() {
		return "departamentos/nuevoDepartamento"; // ubicacion fisica page
	}

}
