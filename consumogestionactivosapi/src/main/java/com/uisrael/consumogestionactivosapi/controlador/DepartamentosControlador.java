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

import com.uisrael.consumogestionactivosapi.modelo.dto.request.DepartamentosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.DepartamentosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IDepartamentosServicio;
import com.uisrael.consumogestionactivosapi.service.IUbicacionesServicio;

@Controller
@RequestMapping("/departamentos") // url
public class DepartamentosControlador {

	@Autowired
	private IDepartamentosServicio servicioDepartamento;

	@Autowired
	private IUbicacionesServicio servicioUbicacion;

	@GetMapping
	public String listarDepartamentos(Model model) {
		List<DepartamentosResponseDTO> contenidoBD = servicioDepartamento.listarDepartamentos();
		model.addAttribute("listadepartamento", contenidoBD);
		return "departamentos/listarDepartamentos"; // ubicacion fisica page
	}

	@GetMapping("/nuevo-departamento")
	public String nuevoDepartamento(Model model) {
		DepartamentosRequestDTO departamento = new DepartamentosRequestDTO();
		departamento.setEstado(true); // ✅ por defecto ACTIVO
		model.addAttribute("listaubicacion", servicioUbicacion.listarUbicaciones());
		model.addAttribute("departamento", departamento);
		return "departamentos/nuevoDepartamento"; // ubicacion fisica page
	}

	@GetMapping("/editar-departamento/{id}")
	public String modificarDepartamento(@PathVariable Integer id, Model model) {
		model.addAttribute("listaubicacion", servicioUbicacion.listarUbicaciones());
		DepartamentosResponseDTO departamento = servicioDepartamento.obtenerPorId(id);

		model.addAttribute("departamento", departamento);

		return "departamentos/editarDepartamento"; // ubicacion fisica page
	}

	@PostMapping
	public String guardarDepartametno(@ModelAttribute DepartamentosRequestDTO departamento, Model model) {

		boolean hayErrores = false;

		if (departamento.getNombre() == null || departamento.getNombre().trim().isEmpty()) {
			model.addAttribute("errorNombre", "El nombre es obligatorio");
			hayErrores = true;
		}

		// 🔴 Si hay errores, regreso al formulario
		if (hayErrores) {
			model.addAttribute("departamento", departamento);
			return ubicacionesFormulario(departamento);
		}

		if (departamento.getIdDepartamento() > 0) {
			servicioDepartamento.actualizarDepartamento(departamento.getIdDepartamento(), departamento);
		} else {
			servicioDepartamento.crearDepartamento(departamento); // envia al api para guardar
		}
		return "redirect:/departamentos";
	}

	private String ubicacionesFormulario(DepartamentosRequestDTO departamento) {
		return (departamento.getIdDepartamento() > 0) ? "departamentos/editarDepartamento"
				: "departamentos/nuevoDepartamento";
	}

	@PostMapping("/eliminar-departamento")
	public String eliminarLogico(@org.springframework.web.bind.annotation.RequestParam Integer idDepartamento) {
		servicioDepartamento.actualizarEstado(idDepartamento, false);
		return "redirect:/departamentos";
	}
}
