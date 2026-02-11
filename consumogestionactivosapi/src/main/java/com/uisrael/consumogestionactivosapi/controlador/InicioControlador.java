package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.uisrael.consumogestionactivosapi.security.SesionUsuario;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InicioControlador {

	private final SesionUsuario sesionUsuario;

	@ModelAttribute("sesionUsuario")
	public SesionUsuario obtenerSesionUsuario() {
		return sesionUsuario;
	}

	@GetMapping("/inicio")
	public String mostrarInicio() {
		return "inicio";
	}
}
