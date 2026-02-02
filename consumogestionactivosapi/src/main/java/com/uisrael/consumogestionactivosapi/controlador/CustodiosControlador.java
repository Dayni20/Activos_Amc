package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;

@Controller
@RequestMapping("/custodios")
public class CustodiosControlador {

	@Autowired
	private ICustodiosServicio servicioCustodios;

	// =========================
	// LISTAR
	// =========================
	@GetMapping
	public String listar(Model model) {
		List<CustodiosResponseDTO> lista = servicioCustodios.listarCustodios();
		lista.sort(Comparator.comparing(CustodiosResponseDTO::getIdCustodio));
		model.addAttribute("listarcustodios", lista);
		return "Custodios/listarCustodios";
	}

	// =========================
	// FORM NUEVO
	// =========================
	@GetMapping("/nuevo-custodio")
	public String nuevo(Model model) {
		CustodiosRequestDTO dto = new CustodiosRequestDTO();
		dto.setEstado(true);
		model.addAttribute("custodio", dto);
		return "Custodios/nuevoCustodio";
	}

	// =========================
	// GUARDAR (CREAR / EDITAR)
	// =========================
	@PostMapping
	public String guardar(@ModelAttribute CustodiosRequestDTO custodio, Model model) {

		boolean hayErrores = false;

		if (custodio.getNombre() == null || custodio.getNombre().trim().isEmpty()) {
			model.addAttribute("errorNombre", "El nombre es obligatorio");
			hayErrores = true;
		}

		if (custodio.getCedula() == null || custodio.getCedula().trim().isEmpty()) {
			model.addAttribute("errorCedula", "La cédula es obligatoria");
			hayErrores = true;
		}

		if (hayErrores) {
			model.addAttribute("custodio", custodio);
			return "Custodios/nuevoCustodio";
		}

		// crear / editar
		if (custodio.getIdCustodio() > 0) {
			servicioCustodios.actualizarCustodio(custodio.getIdCustodio(), custodio);
		} else {
			servicioCustodios.crearCustodio(custodio);
		}

		return "redirect:/custodios";
	}

	// =========================
	// EDITAR (GET) - si lo tienes
	// =========================
	@GetMapping("/editar-custodio/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		CustodiosResponseDTO dto = servicioCustodios.obtenerPorId(id);

		CustodiosRequestDTO req = new CustodiosRequestDTO();
		req.setIdCustodio(dto.getIdCustodio());
		req.setNombre(dto.getNombre());
		req.setCedula(dto.getCedula());
		req.setCorreo(dto.getCorreo());
		req.setTelefono(dto.getTelefono());
		req.setEstado(dto.isEstado());

		model.addAttribute("custodio", req);
		return "Custodios/editarCustodio";
	}

	// =========================
	// TOGGLE (ACTIVAR / DESACTIVAR) - PARA MODAL
	// =========================
	@PostMapping("/toggle-custodio")
	public String toggle(@RequestParam Integer idCustodio, @RequestParam boolean estado) {

		// estado viene del modal (true/false)
		servicioCustodios.actualizarEstado(idCustodio, estado);
		return "redirect:/custodios";
	}

	// =========================
	// (Si quieres mantener rutas separadas)
	// =========================
	@PostMapping("/eliminar-custodio")
	public String desactivar(@RequestParam Integer idCustodio) {
		servicioCustodios.actualizarEstado(idCustodio, false);
		return "redirect:/custodios";
	}

	@PostMapping("/activar-custodio")
	public String activar(@RequestParam Integer idCustodio) {
		servicioCustodios.actualizarEstado(idCustodio, true);
		return "redirect:/custodios";
	}
}
