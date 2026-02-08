package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CargosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.DepartamentosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICargosServicio;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;
import com.uisrael.consumogestionactivosapi.service.IDepartamentosServicio;

@Controller
@RequestMapping("/custodios")
public class CustodiosControlador {

	@Autowired
	private ICustodiosServicio servicioCustodios;

	@Autowired
	private IDepartamentosServicio servicioDepartamento;

	@Autowired
	private ICargosServicio servicioCargo;

	@GetMapping
	public String listar(Model model) {
		List<CustodiosResponseDTO> lista = servicioCustodios.listarCustodios();
		lista.sort(Comparator.comparing(CustodiosResponseDTO::getIdCustodio));
		model.addAttribute("listarcustodios", lista);
		return "Custodios/listarCustodios";
	}

	@GetMapping("/nuevo-custodio")
	public String nuevo(Model model) {
		CustodiosRequestDTO dto = new CustodiosRequestDTO();
		dto.setEstado(true);

		dto.setFkDepartamento(new DepartamentosRequestDTO());
		dto.getFkDepartamento().setIdDepartamento(0);

		var departamentosActivos = servicioDepartamento.listarDepartamentos().stream().filter(u -> u.isEstado())
				.toList();

		var cargosActivos = servicioCargo.listarCargos().stream().filter(u -> u.isEstado()).toList();

		model.addAttribute("listadepartamento", departamentosActivos);
		model.addAttribute("listacargo", cargosActivos);

		model.addAttribute("custodio", dto);
		return "Custodios/nuevoCustodio";
	}

	@GetMapping("/editar-custodio/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		CustodiosResponseDTO dto = servicioCustodios.obtenerPorId(id);

		Integer idDepartamento = dto.getFkDepartamento().getIdDepartamento();

		model.addAttribute("listadepartamento",
				servicioDepartamento.listarDepartamentos().stream().filter(
						departamento -> departamento.isEstado() || departamento.getIdDepartamento() == idDepartamento)
						.toList());

		Integer idCargo = dto.getFkCargo().getIdCargo();

		model.addAttribute("listacargo", servicioCargo.listarCargos().stream()
				.filter(cargo -> cargo.isEstado() || cargo.getIdCargo() == idCargo).toList());

		model.addAttribute("custodio", dto);

		return "Custodios/editarCustodio";
	}

	@PostMapping
	public String guardar(@ModelAttribute CustodiosRequestDTO custodio, Model model) {

		// 🔒 Asegura objeto anidado
		if (custodio.getFkDepartamento() == null) {
			custodio.setFkDepartamento(new DepartamentosRequestDTO());
		}

		if (custodio.getFkCargo() == null) {
			custodio.setFkCargo(new CargosRequestDTO());
		}

		boolean hayErrores = false;

		if (custodio.getFechaIngreso() == null) {
			model.addAttribute("errorFechaInicio", "La fecha de ingreso es obligatoria");
			hayErrores = true;
		}

		if (custodio.getNombre() == null || custodio.getNombre().trim().isEmpty()) {
			model.addAttribute("errorNombre", "El nombre es obligatorio");
			hayErrores = true;
		}

		if (custodio.getCedula() == null || custodio.getCedula().trim().isEmpty()) {
			model.addAttribute("errorCedula", "La cédula es obligatoria");
			hayErrores = true;
		} else {
			// 2️⃣ Cedula no repetido
			boolean cedulaRepetida;

			if (custodio.getIdCustodio() > 0) {
				// edición
				cedulaRepetida = servicioCustodios.existeCedulaParaOtro(custodio.getCedula().trim(),
						custodio.getIdCustodio());
			} else {
				// creación
				cedulaRepetida = servicioCustodios.existeCedula(custodio.getCedula().trim());
			}

			if (cedulaRepetida) {
				model.addAttribute("errorCedula", "Ya existe un empleado con esa cédula");
				hayErrores = true;
			}

			if (custodio.getCedula() != null && !custodio.getCedula().matches("\\d+")) {
				model.addAttribute("errorCedula", "La cédula solo debe contener números");
				hayErrores = true;
			}

		}

		if (custodio.getTelefono() != null && !custodio.getTelefono().isBlank()) {
			if (!custodio.getTelefono().matches("\\d+")) {
				model.addAttribute("errorTelefono", "El teléfono solo debe contener números");
				hayErrores = true;
			}
		}

		if (custodio.getCorreo() == null || custodio.getCorreo().trim().isEmpty()) {
			model.addAttribute("errorCorreo", "El correo es obligatorio");
			hayErrores = true;
		} else {

			// 2️⃣ Correo no repetido
			boolean correoRepetido;

			if (custodio.getIdCustodio() > 0) {
				// edición
				correoRepetido = servicioCustodios.existeCorreoParaOtro(custodio.getCorreo().trim(),
						custodio.getIdCustodio());
			} else {
				// creación
				correoRepetido = servicioCustodios.existeCorreo(custodio.getCorreo().trim());
			}

			if (correoRepetido) {
				model.addAttribute("errorCorreo", "Ya existe un empleado con ese correo");
				hayErrores = true;
			}

			if (!custodio.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				model.addAttribute("errorCorreo", "Formato de correo inválido");
				hayErrores = true;
			}
		}

		// 3️⃣ Ubicación obligatoria
		if (custodio.getFkDepartamento().getIdDepartamento() <= 0) {
			model.addAttribute("errorSeleccionDepartamento", "Debe seleccionar un departamento");
			hayErrores = true;
		}

		if (custodio.getFkCargo().getIdCargo() <= 0) {
			model.addAttribute("errorSeleccionCargo", "Debe seleccionar un cargo");
			hayErrores = true;
		}

		if (hayErrores) {

			model.addAttribute("listadepartamento",
					servicioDepartamento.listarDepartamentos().stream().filter(u -> u.isEstado()).toList());

			model.addAttribute("listacargo", servicioCargo.listarCargos().stream().filter(u -> u.isEstado()).toList());

			model.addAttribute("custodio", custodio);

			return ubicacionesFormulario(custodio);
		}

		if (custodio.getIdCustodio() > 0) {
			servicioCustodios.actualizarCustodio(custodio.getIdCustodio(), custodio);
		} else {
			custodio.setEstado(true);
			servicioCustodios.crearCustodio(custodio);
		}

		return "redirect:/custodios";
	}

	@PostMapping("/toggle-custodio")
	public String toggle(@RequestParam Integer idCustodio, @RequestParam boolean estado) {

		servicioCustodios.actualizarEstado(idCustodio, estado);
		return "redirect:/custodios";
	}

	private String ubicacionesFormulario(CustodiosRequestDTO custodio) {
		return (custodio.getIdCustodio() > 0) ? "Custodios/editarCustodio" : "Custodios/nuevoCustodio";
	}

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
