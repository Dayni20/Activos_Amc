package com.uisrael.consumogestionactivosapi.controlador;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CategoriaEquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.DepartamentosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.EquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.MarcasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.ProveedoresRequestDTO;

import com.uisrael.consumogestionactivosapi.modelo.dto.response.EquiposResponseDTO;

import com.uisrael.consumogestionactivosapi.service.ICategoriaEquiposServicio;
import com.uisrael.consumogestionactivosapi.service.IDepartamentosServicio;
import com.uisrael.consumogestionactivosapi.service.IEquiposServicio;
import com.uisrael.consumogestionactivosapi.service.IMarcasServicio;
import com.uisrael.consumogestionactivosapi.service.IProveedoresServicio;

@Controller
@RequestMapping("/equipos")
public class EquiposControlador {

	@Autowired
	private IEquiposServicio servicioEquipos;

	@Autowired
	private IDepartamentosServicio servicioDepartamentos;

	@Autowired
	private IMarcasServicio servicioMarcas;

	@Autowired
	private IProveedoresServicio servicioProveedores;

	@Autowired
	private ICategoriaEquiposServicio servicioCategoriaEquipos;

	@GetMapping
	public String listarEquipos(Model model) {
		List<EquiposResponseDTO> contenidoBD = servicioEquipos.listarEquipos();
		contenidoBD.sort(Comparator.comparing(EquiposResponseDTO::getIdEquipo));
		model.addAttribute("listarequipos", contenidoBD);
		return "Equipos/listarEquipos";
	}

	@GetMapping("/nuevo-equipo")
	public String nuevoEquipo(Model model) {

		EquiposRequestDTO equipo = new EquiposRequestDTO();
		equipo.setEstado(true);

		equipo.setFkDepartamento(new DepartamentosRequestDTO());
		equipo.getFkDepartamento().setIdDepartamento(0);

		equipo.setFkMarca(new MarcasRequestDTO());
		equipo.getFkMarca().setIdMarca(0);

		equipo.setFkProveedor(new ProveedoresRequestDTO());
		equipo.getFkProveedor().setIdProveedor(0);

		equipo.setFkCategoria(new CategoriaEquiposRequestDTO());
		equipo.getFkCategoria().setIdCategoria(0);

		// combos
		cargarCombos(model, 0, 0, 0, 0);

		model.addAttribute("equipo", equipo);
		return "Equipos/nuevoEquipo";
	}

	@PostMapping
	public String guardarEquipo(@ModelAttribute EquiposRequestDTO equipo, Model model) {

		if (equipo.getFkDepartamento() == null) {
			equipo.setFkDepartamento(new DepartamentosRequestDTO());
			equipo.getFkDepartamento().setIdDepartamento(0);
		}
		if (equipo.getFkMarca() == null) {
			equipo.setFkMarca(new MarcasRequestDTO());
			equipo.getFkMarca().setIdMarca(0);
		}
		if (equipo.getFkProveedor() == null) {
			equipo.setFkProveedor(new ProveedoresRequestDTO());
			equipo.getFkProveedor().setIdProveedor(0);
		}
		if (equipo.getFkCategoria() == null) {
			equipo.setFkCategoria(new CategoriaEquiposRequestDTO());
			equipo.getFkCategoria().setIdCategoria(0);
		}

		boolean hayErrores = false;

		if (equipo.getTipoEquipo() == null || equipo.getTipoEquipo().trim().isEmpty()) {
			model.addAttribute("errorTipoEquipo", "El tipo de equipo es obligatorio");
			hayErrores = true;
		}

		if (equipo.getModelo() == null || equipo.getModelo().trim().isEmpty()) {
			model.addAttribute("errorModelo", "El modelo es obligatorio");
			hayErrores = true;
		}

		if (equipo.getSerial() == null || equipo.getSerial().trim().isEmpty()) {
			model.addAttribute("errorSerial", "El serial es obligatorio");
			hayErrores = true;
		}

		if (equipo.getFkDepartamento().getIdDepartamento() <= 0) {
			model.addAttribute("errorDepartamento", "Debe seleccionar un departamento");
			hayErrores = true;
		}
		if (equipo.getFkMarca().getIdMarca() <= 0) {
			model.addAttribute("errorMarca", "Debe seleccionar una marca");
			hayErrores = true;
		}
		if (equipo.getFkProveedor().getIdProveedor() <= 0) {
			model.addAttribute("errorProveedor", "Debe seleccionar un proveedor");
			hayErrores = true;
		}
		if (equipo.getFkCategoria().getIdCategoria() <= 0) {
			model.addAttribute("errorCategoria", "Debe seleccionar una categoría");
			hayErrores = true;
		}

		if (hayErrores) {
			int idDep = equipo.getFkDepartamento().getIdDepartamento();
			int idMarca = equipo.getFkMarca().getIdMarca();
			int idProv = equipo.getFkProveedor().getIdProveedor();
			int idCat = equipo.getFkCategoria().getIdCategoria();

			cargarCombos(model, idDep, idMarca, idProv, idCat);
			model.addAttribute("equipo", equipo);
			return "Equipos/nuevoEquipo"; // solo nuevo (si luego haces editar, se ajusta)
		}

		if (equipo.getIdEquipo() > 0) {
			servicioEquipos.actualizarEquipo(equipo.getIdEquipo(), equipo);
		} else {
			servicioEquipos.crearEquipo(equipo);
		}

		return "redirect:/equipos";
	}

	@PostMapping("/eliminar-equipo")
	public String eliminarLogico(@RequestParam Integer idEquipo) {
		servicioEquipos.actualizarEstado(idEquipo, false);
		return "redirect:/equipos";
	}

	@PostMapping("/activar-equipo")
	public String activarEquipo(@RequestParam Integer idEquipo) {
		servicioEquipos.actualizarEstado(idEquipo, true);
		return "redirect:/equipos";
	}

	private void cargarCombos(Model model, int idDepSel, int idMarcaSel, int idProvSel, int idCatSel) {

		model.addAttribute("listadepartamentos", servicioDepartamentos.listarDepartamentos().stream()
				.filter(d -> d.isEstado() || d.getIdDepartamento() == idDepSel).collect(Collectors.toList()));

		model.addAttribute("listamarcas", servicioMarcas.listarMarca().stream()
				.filter(m -> m.isEstado() || m.getIdMarca() == idMarcaSel).collect(Collectors.toList()));

		model.addAttribute("listaproveedores", servicioProveedores.listarProveedores().stream()
				.filter(p -> p.isEstado() || p.getIdProveedor() == idProvSel).collect(Collectors.toList()));

		model.addAttribute("listacategorias", servicioCategoriaEquipos.listarCategoriaEquipo().stream() // ✅ tu método
																										// real
				.filter(c -> c.isEstado() || c.getIdCategoria() == idCatSel).collect(Collectors.toList()));
	}

	@GetMapping("/reporte-equipo")
	public String listarEquiposReporte(
	        @RequestParam(required = false) String tipo,
	        Model model) {

	    List<EquiposResponseDTO> contenidoBD = servicioEquipos.listarEquipos();

	    // lista de tipos únicos (antes de filtrar)
	    List<String> listaTipos = contenidoBD.stream()
	            .map(EquiposResponseDTO::getTipoEquipo)
	            .filter(t -> t != null && !t.trim().isEmpty())
	            .map(String::trim)
	            .distinct()
	            .sorted(String.CASE_INSENSITIVE_ORDER)
	            .toList();

	    // filtrar si viene tipo
	    if (tipo != null && !tipo.trim().isEmpty()) {
	        String t = tipo.trim();
	        contenidoBD = contenidoBD.stream()
	                .filter(e -> e.getTipoEquipo() != null && e.getTipoEquipo().trim().equalsIgnoreCase(t))
	                .toList();
	    }

	    contenidoBD = contenidoBD.stream()
	            .sorted(Comparator.comparing(EquiposResponseDTO::getIdEquipo))
	            .toList();

	    model.addAttribute("listarequipos", contenidoBD);
	    model.addAttribute("listaTipos", listaTipos);
	    model.addAttribute("tipoSeleccionado", tipo);

	    return "Equipos/reporteEquipos";
	}
	
	
	@GetMapping("/reporte-equipo/excel")
	public ResponseEntity<byte[]> descargarExcelEquipos(
	        @RequestParam(required = false) String tipo) {

	    List<EquiposResponseDTO> data = servicioEquipos.listarEquipos();

	    // mismo filtro que en la vista
	    if (tipo != null && !tipo.trim().isEmpty()) {
	        String t = tipo.trim().toLowerCase();
	        data = data.stream()
	                .filter(e -> e.getTipoEquipo() != null && e.getTipoEquipo().toLowerCase().equals(t))
	                .toList();
	    }

	    try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

	        Sheet sheet = wb.createSheet("Reporte Equipos");

	        CellStyle headerStyle = wb.createCellStyle();
	        Font headerFont = wb.createFont();
	        headerFont.setBold(true);
	        headerStyle.setFont(headerFont);

	        String[] cols = {
	                "ID", "CÓDIGO SAP", "TIPO", "MODELO", "SERIAL", "PROCESADOR",
	                "RAM (GB)", "ALMACENAMIENTO (GB)", "SISTEMA OPERATIVO",
	                "LIC. WINDOWS", "ETIQ. ACTIVO FIJO", "TIPO LIC. OFFICE", "VERSIÓN OFFICE", "UNIÓN DOMINIO",
	                "IP", "MAC", "FECHA COMPRA", "PRECIO COMPRA",
	                "ESTADO EQUIPO", "OBSERVACIÓN",
	                "DEPARTAMENTO", "MARCA", "PROVEEDOR", "CATEGORÍA",
	                "ESTADO"
	        };

	        Row header = sheet.createRow(0);
	        for (int i = 0; i < cols.length; i++) {
	            Cell c = header.createCell(i);
	            c.setCellValue(cols[i]);
	            c.setCellStyle(headerStyle);
	        }

	        int r = 1;
	        for (EquiposResponseDTO e : data) {
	            Row row = sheet.createRow(r++);

	            int c = 0;
	            row.createCell(c++).setCellValue(e.getIdEquipo());
	            row.createCell(c++).setCellValue(val(e.getCodigoSap()));
	            row.createCell(c++).setCellValue(val(e.getTipoEquipo()));
	            row.createCell(c++).setCellValue(val(e.getModelo()));
	            row.createCell(c++).setCellValue(val(e.getSerial()));
	            row.createCell(c++).setCellValue(val(e.getProcesador()));
	            row.createCell(c++).setCellValue(numOrText(e.getMemoriaRamGb()));
	            row.createCell(c++).setCellValue(numOrText(e.getCapacidadAlmacenamientoGb()));
	            row.createCell(c++).setCellValue(val(e.getSistemaOperativo()));

	            row.createCell(c++).setCellValue(boolSiNo(e.getLicenciaWindowsActivada()));
	            row.createCell(c++).setCellValue(boolSiNo(e.getEtiquetaActivoFijo()));
	            row.createCell(c++).setCellValue(val(e.getTipoLicenciaOffice()));
	            row.createCell(c++).setCellValue(val(e.getVersionOffice()));
	            row.createCell(c++).setCellValue(boolSiNo(e.getUnionDominio()));

	            row.createCell(c++).setCellValue(val(e.getIp()));
	            row.createCell(c++).setCellValue(val(e.getMac()));
	            row.createCell(c++).setCellValue(val(e.getFechaCompra())); // si es LocalDate, lo formateas
	            row.createCell(c++).setCellValue(val(e.getPrecioCompra())); // si es BigDecimal, puedes setear double

	            row.createCell(c++).setCellValue(val(e.getEstadoEquipo()));
	            row.createCell(c++).setCellValue(val(e.getObservacionEquipo()));

	            row.createCell(c++).setCellValue(e.getFkDepartamento() != null ? val(e.getFkDepartamento().getNombre()) : "-");
	            row.createCell(c++).setCellValue(e.getFkMarca() != null ? val(e.getFkMarca().getNombre()) : "-");
	            row.createCell(c++).setCellValue(e.getFkProveedor() != null ? val(e.getFkProveedor().getNombre()) : "-");
	            row.createCell(c++).setCellValue(e.getFkCategoria() != null ? val(e.getFkCategoria().getNombre()) : "-");

	            row.createCell(c++).setCellValue(e.isEstado() ? "Activo" : "Inactivo");
	        }

	        for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);

	        wb.write(out);

	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
	        String suf = (tipo != null && !tipo.isBlank()) ? "_" + tipo.trim().toLowerCase() : "";
	        String filename = "reporte_equipos" + suf + "_" + timestamp + ".xlsx";

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	                .body(out.toByteArray());

	    } catch (Exception ex) {
	        throw new RuntimeException("Error generando Excel de equipos", ex);
	    }
	}

	// helpers (ponlos como métodos privados del controlador)
	private String val(Object x) { return (x == null) ? "-" : String.valueOf(x); }
	private String boolSiNo(Boolean b) { return (b != null && b) ? "Sí" : "No"; }
	private String numOrText(Object n) { return (n == null) ? "-" : String.valueOf(n); }
}
