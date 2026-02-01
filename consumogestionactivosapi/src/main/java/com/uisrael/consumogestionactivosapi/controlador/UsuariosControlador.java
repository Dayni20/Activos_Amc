package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.UsuariosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.RolesResponseDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.UsuariosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IUsuariosServicio;
import com.uisrael.consumogestionactivosapi.service.IRolesServicio;

@Controller
@RequestMapping("/usuarios")
public class UsuariosControlador {

	@Autowired
	private IUsuariosServicio servicioUsuarios;
	
	@Autowired
	private IRolesServicio servicioRoles;
	
	@GetMapping
	public String listarUsuarios(Model model) {	
		List<UsuariosResponseDTO> contenidoBD = servicioUsuarios.listarUsuario();
		model.addAttribute("listarusuarios", contenidoBD);
		return "usuarios/listarUsuarios";
	}
	
	@GetMapping("/nuevo-usuario")
	public String nuevoUsuario(Model model) {
		model.addAttribute("nuevousuario", new UsuariosRequestDTO());
		List<RolesResponseDTO> roles = servicioRoles.listarRol();
		model.addAttribute("roles", roles);
		return "usuarios/nuevoUsuario";
	}
	
	@PostMapping
	public String guardarUsuario(@ModelAttribute UsuariosRequestDTO nuevousuario) {
		servicioUsuarios.nuevoUsuario(nuevousuario);
		return "redirect:/usuarios"; 
	}
	
	@GetMapping("/editar-usuario")
	public String modificarUsuario() {
		return "usuarios/editarUsuario";
	}
}
