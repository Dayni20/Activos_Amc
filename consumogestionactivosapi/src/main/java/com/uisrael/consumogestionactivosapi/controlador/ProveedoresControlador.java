package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	//GET: EDITAR 
		@GetMapping("/editar/{id}")
		public String editarProveedores(@PathVariable Integer id, Model model) {

		    ProveedoresResponseDTO proveedores = servicioProveedores.obtenerProveedor(id);

		    model.addAttribute("nuevoproveedor", proveedores);

		    return "proveedores/editarProveedores";
		}
		
		// POST:ELIMINAR
		@PostMapping("/eliminar/{id}")
		public String eliminarProveedor(@PathVariable Integer id, Model model) {
			try {
				servicioProveedores.eliminarProveedor(id);
				return "redirect:/proveedores";
			} catch (RuntimeException e) {
				List<ProveedoresResponseDTO> contenidoBD = servicioProveedores.listarProveedores();
				model.addAttribute("listarproveedores", contenidoBD);
				model.addAttribute("errorEliminar", e.getMessage());
				return "proveedores/listarProveedores";
			}
}
}