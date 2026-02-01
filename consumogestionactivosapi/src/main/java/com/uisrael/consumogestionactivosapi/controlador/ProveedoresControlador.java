package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.ProveedoresRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.ProveedoresResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IProveedoresServicio;

@Controller
@RequestMapping("/proveedores") // url
public class ProveedoresControlador {

	@Autowired
	private IProveedoresServicio servicioProveedores;
	
	@GetMapping
	public String listarProveedores(Model model) {
		List<ProveedoresResponseDTO> contenidoBD=	servicioProveedores.listarProveedores();
		model.addAttribute("listarproveedores", contenidoBD);
		return "proveedores/listarProveedores"; // ubicacion fisica page
	}

	 //GET: muestra el formulario
    @GetMapping("/nuevoProveedores")
    public String nuevoProveedores(Model model) {
        model.addAttribute("nuevoproveedor", new ProveedoresRequestDTO());
        return "proveedores/nuevoProveedores";  // ubicacion fisica page
    }
    
    
    //POST: guarda en BD
	@PostMapping
	public String guardarProveedores(@ModelAttribute ProveedoresRequestDTO nuevoproveedor) {
		servicioProveedores.nuevoProveedores(nuevoproveedor);
		return "redirect:/proveedores"; 
	}
	
	
	
	@PutMapping("/editarProveedores")
	public String modificarProveedores() {
		return "proveedores/editarProveedores"; // ubicacion fisica page
	}

}