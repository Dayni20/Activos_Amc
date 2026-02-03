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

import com.uisrael.consumogestionactivosapi.modelo.dto.request.MarcasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.MarcasResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IMarcasServicio;

@Controller
@RequestMapping("/marcas") // url
public class MarcasControlador {

	@Autowired
	private IMarcasServicio servicioMarcas;
	
	@GetMapping
	public String listarMarcas(Model model) {	
	List<MarcasResponseDTO> contenidoBD=	servicioMarcas.listarMarca();
	model.addAttribute("listarmarcas", contenidoBD);
		return "marcas/listarMarcas"; // ubicacion fisica page
	}
	
	//GET: muestra el formulario
	@GetMapping("/nuevaMarcas")
	public String nuevaMarcas(Model model) {
		model.addAttribute("nuevamarca", new MarcasRequestDTO());
		return "marcas/nuevaMarcas"; // ubicacion fisica page
	}
	
	 //POST: guarda en BD
	@PostMapping
	public String guardarMarcas(@ModelAttribute MarcasRequestDTO nuevamarca) {
		servicioMarcas.nuevaMarca(nuevamarca);
		return "redirect:/marcas"; 
	}
	
	//GET EDITAR 
	@GetMapping("/editarMarcas/{id}")
	public String modificarMarcas(@PathVariable Integer id, Model model) {

	    MarcasResponseDTO marca = servicioMarcas.obtenerMarca(id);

	    MarcasRequestDTO dto = new MarcasRequestDTO();
	    dto.setIdMarca(marca.getIdMarca());
	    dto.setNombre(marca.getNombre());
	    dto.setEstado(marca.isEstado());

	    model.addAttribute("editarmarca", dto);
	    return "marcas/editarMarcas";
	}
	
	  // POST EDITAR
    @PostMapping("/editarMarcas")
    public String actualizarMarca(
            @ModelAttribute("editarmarca") MarcasRequestDTO dto) {

        servicioMarcas.actualizarMarca(dto.getIdMarca(), dto);
        return "redirect:/marcas";
    }

}
