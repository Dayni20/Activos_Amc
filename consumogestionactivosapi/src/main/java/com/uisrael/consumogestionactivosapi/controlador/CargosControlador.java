package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CargosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CargosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICargosServicio;

@Controller
@RequestMapping("/cargos") // url
public class CargosControlador {
	
	@Autowired
	private ICargosServicio servicioCargo;

	@GetMapping
	public String listarCargos(Model model) {
		List<CargosResponseDTO> contenidoBD = servicioCargo.listarCargos();
		contenidoBD.sort(Comparator.comparing(CargosResponseDTO::getIdCargo));
		model.addAttribute("listacargo", contenidoBD);
		return "cargos/listarCargos"; // ubicacion fisica page
	}

	@GetMapping("/nuevo-cargo")
	public String nuevoCargo(Model model) {
		CargosRequestDTO cargo = new CargosRequestDTO();
		cargo.setEstado(true);
		model.addAttribute("cargo", cargo);
		return "cargos/nuevoCargo"; // ubicacion fisica page
	}

	@GetMapping("/editar-cargo/{id}")
	public String modificarCargo(@PathVariable Integer id, Model model) {
		CargosResponseDTO cargo = servicioCargo.obtenerPorId(id);

		model.addAttribute("cargo", cargo);

		return "cargos/editarCargo"; // ubicacion fisica page
	}

	@PostMapping
	public String guardarCargo(@ModelAttribute CargosRequestDTO cargo, Model model) {

	    boolean hayErrores = false;

	    // 1️⃣ Nombre obligatorio
	    if (cargo.getNombre() == null || cargo.getNombre().trim().isEmpty()) {
	        model.addAttribute("errorNombre", "El nombre es obligatorio");
	        hayErrores = true;
	    } else {
	        // 2️⃣ Nombre no repetido
	        boolean nombreRepetido;

	        if (cargo.getIdCargo() > 0) {
	            // edición
	            nombreRepetido = servicioCargo.nombreExisteParaOtro(
	            		cargo.getNombre().trim(), cargo.getIdCargo());
	        } else {
	            // creación
	            nombreRepetido = servicioCargo.nombreExiste(cargo.getNombre().trim());
	        }

	        if (nombreRepetido) {
	            model.addAttribute("errorNombre", "Ya existe un cargo con ese nombre");
	            hayErrores = true;
	        }
	    }


	    // 🔴 Si hay errores, vuelve al formulario
	    if (hayErrores) {
	        model.addAttribute("cargo", cargo);
	        return ubicacionesFormulario(cargo);
	    }

	    if (cargo.getIdCargo() <= 0) {
	        cargo.setEstado(true);
	    }

	    // 4️⃣ Guardar
	    if (cargo.getIdCargo() > 0) {
	        servicioCargo.actualizarCargo(cargo.getIdCargo(), cargo);
	    } else {
	        servicioCargo.crearCargo(cargo);
	    }

	    return "redirect:/cargos";
	}

	private String ubicacionesFormulario(CargosRequestDTO cargo) {
		return (cargo.getIdCargo() > 0) ? "cargos/editarCargo"
				: "cargos/nuevoCargo";
	}

	@PostMapping("/eliminar-cargo")
	public String eliminarLogico(@org.springframework.web.bind.annotation.RequestParam Integer idCargo) {
		servicioCargo.actualizarEstado(idCargo, false);
		return "redirect:/cargos";
	}

}
