package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CategoriaEquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CategoriaEquiposResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICategoriaEquiposServicio;

@Controller
@RequestMapping("/categorias-equipo")
public class CategoriaEquiposControlador {

	@Autowired
	private ICategoriaEquiposServicio servicioCategoriaEquipos;
	
	@GetMapping
	public String listarCategoriaEquipos(Model model) {	
		List<CategoriaEquiposResponseDTO> contenidoBD = servicioCategoriaEquipos.listarCategoriaEquipo();
		model.addAttribute("listarcategorias", contenidoBD);
		return "categorias_equipo/listarCategorias";
	}
	
	@GetMapping("/nueva-categoria")
	public String nuevaCategoria(Model model) {
		model.addAttribute("nuevacategoria", new CategoriaEquiposRequestDTO());
		return "categorias_equipo/nuevaCategoria";
	}
	
	@PostMapping
	public String guardarCategoria(@ModelAttribute CategoriaEquiposRequestDTO nuevacategoria) {
		servicioCategoriaEquipos.nuevoCategoriaEquipo(nuevacategoria);
		return "redirect:/categorias-equipo"; 
	}
	
	@GetMapping("/editar-categoria")
	public String modificarCategoria() {
		return "categorias_equipo/editarCategoria";
	}
}
