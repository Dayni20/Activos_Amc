package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.RolesRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.RolesResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IRolesServicio;

@Controller
@RequestMapping("/roles")
public class RolesControlador {

	@Autowired
	private IRolesServicio servicioRoles;
	
	@GetMapping
	public String listarRoles(Model model) {	
		List<RolesResponseDTO> contenidoBD = servicioRoles.listarRol();
		model.addAttribute("listarroles", contenidoBD);
		return "roles/listarRoles";
	}
	
	@GetMapping("/nuevo-rol")
	public String nuevoRol(Model model) {
		model.addAttribute("nuevorol", new RolesRequestDTO());
		return "roles/nuevoRol";
	}
	
	@PostMapping
	public String guardarRol(@ModelAttribute RolesRequestDTO nuevorol) {
		servicioRoles.nuevoRol(nuevorol);
		return "redirect:/roles"; 
	}
	
	@GetMapping("/editar-rol")
	public String modificarRol() {
		return "roles/editarRol";
	}
}
