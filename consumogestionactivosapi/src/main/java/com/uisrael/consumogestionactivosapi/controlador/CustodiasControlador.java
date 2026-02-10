package com.uisrael.consumogestionactivosapi.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.EquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiasResponseDTO;
import com.uisrael.consumogestionactivosapi.security.SesionUsuario;
import com.uisrael.consumogestionactivosapi.service.ICustodiasServicio;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;
import com.uisrael.consumogestionactivosapi.service.IEquiposServicio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/custodias")
public class CustodiasControlador {

	private final ICustodiasServicio servicioCustodias;
	private final IEquiposServicio servicioEquipos;
	private final ICustodiosServicio servicioCustodios;
	private final SesionUsuario sesionUsuario;

	@ModelAttribute("sesionUsuario")
	public SesionUsuario obtenerSesionUsuario() {
		return sesionUsuario;
	}

	// =========================================================
	// LISTAR (AGRUPADO POR CUSTODIO)
	// =========================================================
	@GetMapping
	public String listarCustodias(Model model) {

	    List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias();

	    // ✅ Key: idCustodio real (si viene fkCustodio, úsalo; si no, usa el campo directo)
	    java.util.function.Function<CustodiasResponseDTO, Integer> idCustodioKey = x -> {
	        if (x == null) return 0;
	        if (x.getFkCustodio() != null) {
	            return x.getFkCustodio().getIdCustodio(); // int -> Integer (autobox)
	        }
	        return x.getIdCustodio(); // fallback
	    };

	    // ✅ Orden para que el agrupado se vea bonito
	    lista.sort(
	        Comparator
	            .comparing(idCustodioKey)
	            .thenComparing(
	                CustodiasResponseDTO::getIdCustodiaEquipo,
	                Comparator.nullsLast(Integer::compareTo)
	            )
	    );

	    // ✅ Estado real por custodio: si alguna fila está activa
	    Map<Integer, Boolean> custodioActiva = lista.stream()
	        .collect(Collectors.groupingBy(
	            idCustodioKey,
	            Collectors.collectingAndThen(
	                Collectors.toList(),
	                dets -> dets.stream().anyMatch(CustodiasResponseDTO::isEstado)
	            )
	        ));

	    // ✅ Resumen por custodio: cabecera = menor idCustodiaEquipo
	    Map<Integer, CustodiasResponseDTO> resumenPorCustodio = lista.stream()
	        .collect(Collectors.groupingBy(
	            idCustodioKey,
	            Collectors.collectingAndThen(
	                Collectors.minBy(
	                    Comparator.comparing(
	                        CustodiasResponseDTO::getIdCustodiaEquipo,
	                        Comparator.nullsLast(Integer::compareTo)
	                    )
	                ),
	                opt -> opt.orElse(null)
	            )
	        ));

	    model.addAttribute("listacustodias", lista);
	    model.addAttribute("custodioActiva", custodioActiva);
	    model.addAttribute("resumenPorCustodio", resumenPorCustodio);

	    return "Custodias/listarCustodias";
	}


	@GetMapping("/cerrar-custodia/custodio/{idCustodio}")
	public String cerrarCustodiaPorCustodio(@PathVariable Integer idCustodio, Model model) {

	    List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
	            .filter(x -> x != null && x.getIdCustodio() == idCustodio.intValue())
	            .sorted(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
	            .toList();

	    if (lista.isEmpty()) return "redirect:/custodias";

	    // ✅ Cabecera = PK menor SOLO de ese custodio
	    CustodiasResponseDTO cabecera = lista.get(0);

	    CustodiasRequestDTO form = new CustodiasRequestDTO();
	    form.setIdCustodio(idCustodio);
	    form.setFechaInicio(cabecera.getFechaInicio());
	    form.setFechaFin(cabecera.getFechaFin() != null ? cabecera.getFechaFin() : LocalDate.now());
	    form.setObservacion(cabecera.getObservacion() != null ? cabecera.getObservacion() : "");

	    model.addAttribute("cabecera", cabecera);
	    model.addAttribute("detalles", lista);
	    model.addAttribute("form", form);

	    return "custodias/cerrarCustodia";
	}

	// =========================================================
	// CERRAR CUSTODIA (PANTALLA CHECKS)
	// =========================================================
	@GetMapping("/cerrar-custodia/{idCustodia}")
	public String cerrarCustodiaForm(@PathVariable Integer idCustodia, Model model) {

		List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
				.filter(x -> x.getIdCustodia() == idCustodia.intValue()).toList();

		if (lista.isEmpty())
			return "redirect:/custodias";

		CustodiasResponseDTO cabecera = lista.get(0);

		// fuente = menor idCustodiaEquipo
		CustodiasResponseDTO fuente = lista.stream().min(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo,
				Comparator.nullsLast(Integer::compareTo))).orElse(cabecera);

		String obs = (fuente.getObservacion() != null) ? fuente.getObservacion().trim() : "";

		LocalDate fechaInicio = fuente.getFechaInicio();
		LocalDate fechaFin = fuente.getFechaFin();

		if (fechaInicio == null) {
			fechaInicio = lista.stream().map(CustodiasResponseDTO::getFechaInicio).filter(f -> f != null).findFirst()
					.orElse(null);
		}

		if (fechaFin == null)
			fechaFin = LocalDate.now();

		CustodiasRequestDTO form = new CustodiasRequestDTO();
		form.setIdCustodia(idCustodia);
		form.setObservacion(obs);
		form.setFechaInicio(fechaInicio);
		form.setFechaFin(fechaFin);

		model.addAttribute("cabecera", cabecera);
		model.addAttribute("detalles", lista);
		model.addAttribute("form", form);

		return "custodias/cerrarCustodia";
	}

	// =========================================================
	// CERRAR CUSTODIA (GUARDAR)
	// =========================================================
	@PostMapping("/cerrar-custodia")
	public String cerrarCustodiaGuardar(@ModelAttribute("form") CustodiasRequestDTO form,
	                                    HttpSession session,
	                                    Model model) {

	    int idCustodio = form.getIdCustodio();

	    // ✅ Traer SOLO filas del custodio que estás cerrando
	    List<CustodiasResponseDTO> todas = servicioCustodias.listarCustodias().stream()
	            .filter(x -> x != null && x.getIdCustodio() == idCustodio)
	            .sorted(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
	            .toList();

	    if (todas.isEmpty()) return "redirect:/custodias";

	    // ✅ Cabecera = PK menor del custodio
	    CustodiasResponseDTO cabecera = todas.get(0);
	    int pkCabecera = cabecera.getIdCustodiaEquipo();

	    // ✅ valores editados
	    String obsFinal = (form.getObservacion() == null) ? "" : form.getObservacion().trim();
	    LocalDate fechaFinFinal = (form.getFechaFin() != null) ? form.getFechaFin() : LocalDate.now();

	    // ✅ checks (PK devueltos)
	    Set<Integer> pksDevueltos = new HashSet<>();
	    if (form.getDetallesEntregados() != null) {
	        pksDevueltos.addAll(form.getDetallesEntregados());
	    }

	    // ======================================================
	    // 1) ACTUALIZAR CABECERA (PK menor) con obs + fechaFin
	    // ======================================================
	    CustodiasRequestDTO updCab = new CustodiasRequestDTO();
	    updCab.setIdCustodio(idCustodio);
	    updCab.setFechaInicio(cabecera.getFechaInicio());
	    updCab.setFechaFin(fechaFinFinal);
	    updCab.setObservacion(obsFinal);
	    updCab.setEstado(false);

	    CustodiosRequestDTO cCab = new CustodiosRequestDTO();
	    cCab.setIdCustodio(idCustodio);
	    updCab.setFkCustodio(cCab);

	    EquiposRequestDTO eCab = new EquiposRequestDTO();
	    eCab.setIdEquipo(cabecera.getFkEquipo().getIdEquipo());
	    updCab.setEquipos(List.of(eCab));

	    servicioCustodias.actualizarCustodia(pkCabecera, updCab);

	    // ======================================================
	    // 2) ACTUALIZAR SOLO LOS PK MARCADOS (sin tocar observación)
	    // ======================================================
	    for (CustodiasResponseDTO d : todas) {

	        if (!pksDevueltos.contains(d.getIdCustodiaEquipo())) continue;
	        if (d.getIdCustodiaEquipo() == pkCabecera) continue; // ya se actualizó arriba

	        CustodiasRequestDTO upd = new CustodiasRequestDTO();
	        upd.setIdCustodio(idCustodio);
	        upd.setFechaInicio(d.getFechaInicio());
	        upd.setFechaFin(fechaFinFinal); // si tu API exige
	        upd.setEstado(false);

	        // ✅ NO tocar observación en detalles
	        upd.setObservacion(d.getObservacion());

	        CustodiosRequestDTO c = new CustodiosRequestDTO();
	        c.setIdCustodio(idCustodio);
	        upd.setFkCustodio(c);

	        EquiposRequestDTO e = new EquiposRequestDTO();
	        e.setIdEquipo(d.getFkEquipo().getIdEquipo());
	        upd.setEquipos(List.of(e));

	        servicioCustodias.actualizarCustodia(d.getIdCustodiaEquipo(), upd);
	    }

	    // ======================================================
	    // 3) ARMAR ACTA (solo devueltos) PERO OBS = CABECERA ACTUALIZADA
	    // ======================================================
	    List<CustodiasResponseDTO> actaSalida = servicioCustodias.listarCustodias().stream()
	            .filter(x -> x != null && pksDevueltos.contains(x.getIdCustodiaEquipo()))
	            .sorted(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
	            .toList();

	    // ✅ volver a leer la cabecera (ya actualizada) del custodio
	    CustodiasResponseDTO cabeceraActualizada = servicioCustodias.listarCustodias().stream()
	            .filter(x -> x != null && x.getIdCustodio() == idCustodio)
	            .min(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
	            .orElse(null);

	    String obsActa = (cabeceraActualizada != null && cabeceraActualizada.getObservacion() != null)
	            ? cabeceraActualizada.getObservacion()
	            : obsFinal;

	    LocalDate fechaActa = (cabeceraActualizada != null && cabeceraActualizada.getFechaFin() != null)
	            ? cabeceraActualizada.getFechaFin()
	            : fechaFinFinal;

	    // ✅ Pintar para la vista actaSalida (solo mostrar)
	    for (CustodiasResponseDTO x : actaSalida) {
	        x.setObservacion(obsActa);
	        x.setFechaFin(fechaActa);
	    }

	    session.setAttribute("ACTA_SALIDA_RECIENTE", actaSalida);

	    return "redirect:/custodias/actaSalida";
	}
	


	// =========================================================
	// ACTA SALIDA (HTML)
	// =========================================================
	@GetMapping("/actaSalida")
	public String verActaSalida(Model model, HttpSession session) {

	    @SuppressWarnings("unchecked")
	    List<CustodiasResponseDTO> lista = (List<CustodiasResponseDTO>) session.getAttribute("ACTA_SALIDA_RECIENTE");

	    if (lista == null || lista.isEmpty()) {
	        return "redirect:/custodias";
	    }

	    // ✅ Cabecera del acta = PK menor dentro de la sesión
	    CustodiasResponseDTO cabecera = lista.stream()
	            .min(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
	            .orElse(lista.get(0));

	    // ✅ ID custodio real
	    Integer idCustodioReal = cabecera.getIdCustodio();

	    // ✅ Fecha salida: usa fechaFin pintada por el POST
	    LocalDate fechaSalida = (cabecera.getFechaFin() != null) ? cabecera.getFechaFin() : LocalDate.now();

	    // ✅ Observación: usa la del cabecera (ya pintada por el POST)
	    String obs = (cabecera.getObservacion() != null) ? cabecera.getObservacion() : "";

	    model.addAttribute("cabecera", cabecera);
	    model.addAttribute("detalles", lista);

	    model.addAttribute("idCustodioReal", idCustodioReal);
	    model.addAttribute("fechaSalidaReal", fechaSalida);
	    model.addAttribute("observacionReal", obs);

	    return "Custodias/actaSalida";
	}



	// =========================================================
	// PDF ACTA SALIDA
	// =========================================================
	@GetMapping("/acta-salida/pdf")
	public void descargarActaSalidaPdf(HttpSession session, HttpServletResponse response) throws IOException {

		@SuppressWarnings("unchecked")
		List<CustodiasResponseDTO> lista = (List<CustodiasResponseDTO>) session.getAttribute("ACTA_SALIDA_RECIENTE");

		if (lista == null || lista.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}

		CustodiasResponseDTO cab = lista.get(0);

		Integer idCustodioReal = null;

		if (cab.getFkCustodio() != null) {
			idCustodioReal = cab.getFkCustodio().getIdCustodio();
		}

		String nombre = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getNombre()) : "";
		String cedula = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getCedula()) : "";

		response.reset();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=Acta_Salida.pdf");

		Document doc = new Document(PageSize.A4);
		PdfWriter.getInstance(doc, response.getOutputStream());
		doc.open();

		Font title = new Font(Font.HELVETICA, 14, Font.BOLD);
		Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

		doc.add(new Paragraph("ACTA DE SALIDA DE EQUIPOS", title));
		doc.add(new Paragraph(" ", normal));

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		doc.add(new Paragraph("ID Custodio: " + (idCustodioReal != null ? idCustodioReal : 0), normal));
		doc.add(new Paragraph("Custodio: " + nombre, normal));
		doc.add(new Paragraph("Cédula: " + cedula, normal));
		LocalDate fechaFinEditada = (LocalDate) session.getAttribute("ACTA_SALIDA_FECHA_FIN");
		LocalDate fechaSalida = (fechaFinEditada != null) ? fechaFinEditada : LocalDate.now();

		doc.add(new Paragraph("Fecha salida: " + fechaSalida.format(fmt), normal));

		doc.add(new Paragraph("Observación: " + nvl(cab.getObservacion()), normal));
		doc.add(new Paragraph(" ", normal));

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);

		table.addCell(headerCell("ID Equipo"));
		table.addCell(headerCell("Código"));
		table.addCell(headerCell("Tipo"));
		table.addCell(headerCell("Modelo"));
		table.addCell(headerCell("Serial"));

		Set<Integer> seen = new HashSet<>();

		for (CustodiasResponseDTO it : lista) {
			if (it.getFkEquipo() == null)
				continue;
			Integer idEq = it.getFkEquipo().getIdEquipo();
			if (idEq == null)
				continue;
			if (!seen.add(idEq))
				continue;

			table.addCell(cell(String.valueOf(idEq)));
			table.addCell(cell(nvl(it.getFkEquipo().getCodigoSap())));
			table.addCell(cell(nvl(it.getFkEquipo().getTipoEquipo())));
			table.addCell(cell(nvl(it.getFkEquipo().getModelo())));
			table.addCell(cell(nvl(it.getFkEquipo().getSerial())));
		}

		doc.add(table);

		doc.add(new Paragraph(" ", normal));
		doc.add(new Paragraph("Firma Custodio: ____________________________", normal));
		doc.add(new Paragraph("Firma Responsable TI: ______________________", normal));

		doc.close();
	}

	// =========================================================
	// HELPERS PDF
	// =========================================================
	private PdfPCell headerCell(String text) {
		PdfPCell c = new PdfPCell(new Phrase(text));
		c.setPadding(5);
		return c;
	}

	private PdfPCell cell(String text) {
		PdfPCell c = new PdfPCell(new Phrase(text));
		c.setPadding(5);
		return c;
	}

	private String nvl(String s) {
		return (s == null) ? "" : s;
	}

	// =========================================================
	// ELIMINAR / ACTIVAR
	// =========================================================
	@PostMapping("/eliminar-custodia")
	public String eliminarLogico(@RequestParam Integer idCustodiaEquipo) {
		servicioCustodias.actualizarEstado(idCustodiaEquipo, false);
		return "redirect:/custodias";
	}

	@PostMapping("/activar-custodia")
	public String activar(@RequestParam Integer idCustodiaEquipo) {
		servicioCustodias.actualizarEstado(idCustodiaEquipo, true);
		return "redirect:/custodias";
	}
    // =========================
    // FORM NUEVA CUSTODIA (MULTI EQUIPOS)
    // =========================
    @GetMapping("/nueva-custodia")
    public String nuevaCustodia(Model model) {

        CustodiasRequestDTO custodia = new CustodiasRequestDTO();
        custodia.setEstado(true);

        custodia.setFkCustodio(new CustodiosRequestDTO());
        custodia.getFkCustodio().setIdCustodio(0);

        custodia.setFkEquipo(null);

        var equiposActivos = servicioEquipos.listarEquipos().stream()
                .filter(e -> e.isEstado())
                .toList();

        var custodiosActivos = servicioCustodios.listarCustodios().stream()
                .filter(c -> c.isEstado())
                .toList();

        model.addAttribute("listaequipos", equiposActivos);
        model.addAttribute("listacustodios", custodiosActivos);
        model.addAttribute("custodia", custodia);

        return "custodias/nuevocustodia";
    }
 // =========================================================
 // GUARDAR NUEVA CUSTODIA (FORM NUEVA CUSTODIA)
 // =========================================================
 @PostMapping
 public String guardarCustodia(@ModelAttribute("custodia") CustodiasRequestDTO custodia,
                               Model model,
                               HttpSession session) {

     // 🔒 Validaciones mínimas
     if (custodia.getFkCustodio() == null || custodia.getFkCustodio().getIdCustodio() <= 0) {
         model.addAttribute("error", "Debe seleccionar un custodio");
         return "custodias/nuevocustodia";
     }

     if (custodia.getEquiposSeleccionados() == null || custodia.getEquiposSeleccionados().isEmpty()) {
         model.addAttribute("error", "Debe seleccionar al menos un equipo");
         return "custodias/nuevocustodia";
     }

     // Estado activo
     custodia.setEstado(true);

     // Convertir IDs de equipos a objetos
     List<EquiposRequestDTO> equipos = custodia.getEquiposSeleccionados().stream()
             .distinct()
             .map(id -> {
                 EquiposRequestDTO e = new EquiposRequestDTO();
                 e.setIdEquipo(id);
                 return e;
             })
             .toList();

     custodia.setEquipos(equipos);
     custodia.setFkEquipo(null);

     // Llamar al servicio
     List<CustodiasResponseDTO> creados = servicioCustodias.crearCustodiaActa(custodia);

     if (creados == null || creados.isEmpty()) {
         model.addAttribute("error", "No se pudo crear la custodia");
         return "custodias/nuevocustodia";
     }

     // Guardar en sesión para el acta
     session.setAttribute("ACTA_ENTREGA_RECIENTE", creados);

     return "redirect:/custodias/actaEntrega";
 }
//=========================================================
//ACTA ENTREGA (HTML) - lee la sesión
//=========================================================
@GetMapping("/actaEntrega")
public String verActaEntrega(Model model, HttpSession session) {

  @SuppressWarnings("unchecked")
  List<CustodiasResponseDTO> lista =
          (List<CustodiasResponseDTO>) session.getAttribute("ACTA_ENTREGA_RECIENTE");

  if (lista == null || lista.isEmpty()) {
      return "redirect:/custodias";
  }

  // cabecera = menor idCustodiaEquipo
  CustodiasResponseDTO cabecera = lista.stream()
          .min(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
          .orElse(lista.get(0));

  model.addAttribute("cabecera", cabecera);
  model.addAttribute("detalles", lista);

  return "custodias/actaEntrega";
}
@GetMapping("/acta-entrega/pdf")
public void descargarActaEntregaPdf(HttpSession session, HttpServletResponse response) throws IOException {
    @SuppressWarnings("unchecked")
    List<CustodiasResponseDTO> lista =
            (List<CustodiasResponseDTO>) session.getAttribute("ACTA_ENTREGA_RECIENTE");

    if (lista == null || lista.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return;
    }

    CustodiasResponseDTO cab = lista.get(0);

    response.reset();
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=Acta_Entrega.pdf");

    Document doc = new Document(PageSize.A4);
    PdfWriter.getInstance(doc, response.getOutputStream());
    doc.open();

    Font title = new Font(Font.HELVETICA, 14, Font.BOLD);
    Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

    doc.add(new Paragraph("ACTA DE ENTREGA DE EQUIPOS", title));
    doc.add(new Paragraph(" ", normal));

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String nombre = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getNombre()) : "";
    String cedula = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getCedula()) : "";

    doc.add(new Paragraph("Custodio: " + nombre, normal));
    doc.add(new Paragraph("Cédula: " + cedula, normal));
    doc.add(new Paragraph("Fecha inicio: " + (cab.getFechaInicio() != null ? cab.getFechaInicio().format(fmt) : ""), normal));
    doc.add(new Paragraph("Observación: " + nvl(cab.getObservacion()), normal));
    doc.add(new Paragraph(" ", normal));

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.addCell(headerCell("ID"));
    table.addCell(headerCell("Código"));
    table.addCell(headerCell("Tipo"));
    table.addCell(headerCell("Modelo"));
    table.addCell(headerCell("Serial"));

    Set<Integer> seen = new HashSet<>();

    for (CustodiasResponseDTO it : lista) {
        if (it.getFkEquipo() == null) continue;
        Integer idEq = it.getFkEquipo().getIdEquipo();
        if (idEq == null || !seen.add(idEq)) continue;

        table.addCell(cell(String.valueOf(idEq)));
        table.addCell(cell(nvl(it.getFkEquipo().getCodigoSap())));
        table.addCell(cell(nvl(it.getFkEquipo().getTipoEquipo())));
        table.addCell(cell(nvl(it.getFkEquipo().getModelo())));
        table.addCell(cell(nvl(it.getFkEquipo().getSerial())));
    }

    doc.add(table);
    doc.close();
}
//=========================================================
//ACTA ENTREGA (HTML) POR CUSTODIO
//=========================================================
@GetMapping("/acta-entrega/custodio/{idCustodio}")
public String verActaEntregaPorCustodio(@PathVariable Integer idCustodio, Model model) {

 List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
         .filter(x -> x != null
                   && x.getFkCustodio() != null
                   && x.getFkCustodio().getIdCustodio() == idCustodio)
         .sorted(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo))
         .toList();

 if (lista.isEmpty()) {
     return "redirect:/custodias";
 }

 // cabecera = PK menor
 CustodiasResponseDTO cabecera = lista.get(0);

 model.addAttribute("cabecera", cabecera);
 model.addAttribute("detalles", lista);

 return "custodias/actaEntrega";
}

}
