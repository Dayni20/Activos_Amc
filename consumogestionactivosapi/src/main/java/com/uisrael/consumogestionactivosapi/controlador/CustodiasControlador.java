package com.uisrael.consumogestionactivosapi.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // =========================
    // LISTAR
    // =========================
    @GetMapping
    public String listarCustodias(Model model) {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias();

        // ✅ Ordenar por CUSTODIO para que el agrupado en Thymeleaf NO repita
        lista.sort(
            Comparator.comparing((CustodiasResponseDTO x) -> x.getFkCustodio().getIdCustodio())
                      .thenComparing(CustodiasResponseDTO::getIdCustodia, Comparator.nullsLast(Comparator.naturalOrder()))
                      .thenComparing(CustodiasResponseDTO::getIdCustodiaEquipo, Comparator.nullsLast(Comparator.naturalOrder()))
        );

        // ✅ true = ACTIVO (queda al menos 1 detalle activo)
        // ✅ false = CERRADO (ya no queda ninguno activo)
        java.util.Map<Integer, Boolean> custodiaActiva = lista.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                CustodiasResponseDTO::getIdCustodia,
                java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.toList(),
                    dets -> dets.stream().anyMatch(CustodiasResponseDTO::isEstado)
                )
            ));

        model.addAttribute("listacustodias", lista);
        model.addAttribute("custodiaActiva", custodiaActiva);

        return "Custodias/listarCustodias";
    }

    // =========================
    // CERRAR CUSTODIA (PANTALLA CON CHECKS)
    // =========================
    @GetMapping("/cerrar-custodia/{idCustodia}")
    public String cerrarCustodiaForm(@PathVariable Integer idCustodia, Model model) {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getIdCustodia() == idCustodia.intValue())
                .toList();

        if (lista.isEmpty()) {
			return "redirect:/custodias";
		}

        // ✅ Cabecera solo para mostrar custodio (nombre, cédula, etc.)
        CustodiasResponseDTO cabecera = lista.get(0);

        // ✅ 1) Registro "fuente" = el más reciente (mayor idCustodiaEquipo)
        CustodiasResponseDTO fuente = lista.stream()
                .max(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo,
                        Comparator.nullsLast(Integer::compareTo)))
                .orElse(cabecera);

        // ✅ 2) Si el más reciente no tiene observación, usar la primera no vacía
        String obs = (fuente.getObservacion() != null && !fuente.getObservacion().trim().isEmpty())
                ? fuente.getObservacion().trim()
                : lista.stream()
                    .map(CustodiasResponseDTO::getObservacion)
                    .filter(s -> s != null && !s.trim().isEmpty())
                    .findFirst()
                    .orElse("");

        // ✅ 3) Fecha inicio y fin desde la misma API (como en tu JSON)
        LocalDate fechaInicio = fuente.getFechaInicio();
        LocalDate fechaFin = fuente.getFechaFin();

        // si por algo vinieran null, las recuperamos desde cualquier fila
        if (fechaInicio == null) {
            fechaInicio = lista.stream()
                    .map(CustodiasResponseDTO::getFechaInicio)
                    .filter(f -> f != null)
                    .findFirst()
                    .orElse(null);
        }

        if (fechaFin == null) {
            fechaFin = lista.stream()
                    .map(CustodiasResponseDTO::getFechaFin)
                    .filter(f -> f != null)
                    .findFirst()
                    .orElse(LocalDate.now());
        }

        // ✅ Form precargado EXACTO como la API
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

    // =========================
    // CERRAR CUSTODIA (GUARDAR)
    // ✅ Envía JSON EXACTO como tu Postman:
    // {
    	//   fechaInicio, fechaFin, observacion, estado,
    //   fkCustodio:{idCustodio}, equipos:[{idEquipo}]
    // }
    // =========================
    @PostMapping("/cerrar-custodia")
    public String cerrarCustodiaGuardar(@ModelAttribute("form") CustodiasRequestDTO form,
                                        HttpSession session,
                                        Model model) {

        // ✅ 0) Normaliza observación (evita null / espacios)
        String obsFinal = (form.getObservacion() == null) ? "" : form.getObservacion().trim();
        form.setObservacion(obsFinal);

        // ✅ Validación: deben marcar al menos 1 equipo
        if (form.getDetallesEntregados() == null || form.getDetallesEntregados().isEmpty()) {

            List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                    .filter(x -> x.getIdCustodia() == form.getIdCustodia())
                    .toList();

            model.addAttribute("cabecera", lista.isEmpty() ? null : lista.get(0));
            model.addAttribute("detalles", lista);
            model.addAttribute("form", form);
            model.addAttribute("error", "Debe seleccionar al menos un equipo devuelto");

            return "custodias/cerrarCustodia";
        }

        // 1) Todas las líneas de esa custodia
        List<CustodiasResponseDTO> todas = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getIdCustodia() == form.getIdCustodia())
                .toList();

        if (todas.isEmpty()) {
			return "redirect:/custodias";
		}

        // ✅ Si por alguna razón NO vino fechaInicio desde el hidden, la recuperamos de la cabecera
        CustodiasResponseDTO cabecera = todas.get(0);
        if (form.getFechaInicio() == null) {
            form.setFechaInicio(cabecera.getFechaInicio());
        }

        // ✅ Si no vino fechaFin, usa hoy
        if (form.getFechaFin() == null) {
            form.setFechaFin(LocalDate.now());
        }

        // 2) Solo las líneas marcadas (devueltas)
        List<CustodiasResponseDTO> devueltos = todas.stream()
                .filter(x -> form.getDetallesEntregados().contains(x.getIdCustodiaEquipo()))
                .toList();

        // 3) ✅ Por cada detalle marcado: actualizar SOLO con lo del FORM (misma observación / fechas)
        for (CustodiasResponseDTO d : devueltos) {

            CustodiasRequestDTO upd = new CustodiasRequestDTO();

            // ✅ IMPORTANTE: usar SIEMPRE lo que viene del formulario
            upd.setFechaInicio(form.getFechaInicio());   // ✅ MISMA FECHA INICIO
            upd.setFechaFin(form.getFechaFin());         // ✅ FECHA FIN EDITABLE
            upd.setObservacion(obsFinal);                // ✅ MISMA OBSERVACIÓN EDITADA
            upd.setEstado(false);                        // cerrar

            // ✅ fkCustodio:{idCustodio}
            if (d.getFkCustodio() != null) {
                CustodiosRequestDTO c = new CustodiosRequestDTO();
                c.setIdCustodio(d.getFkCustodio().getIdCustodio());
                upd.setFkCustodio(c);
            }

            // ✅ equipos:[{idEquipo}]
            if (d.getFkEquipo() != null) {
                EquiposRequestDTO e = new EquiposRequestDTO();
                e.setIdEquipo(d.getFkEquipo().getIdEquipo());
                upd.setEquipos(java.util.List.of(e));
            }

            // ✅ id en URL = idCustodiaEquipo
            servicioCustodias.actualizarCustodia(d.getIdCustodiaEquipo(), upd);
        }

        // 4) Guardar para Acta de Salida SOLO devueltos
        session.setAttribute("ACTA_SALIDA_RECIENTE", devueltos);

        // 5) Ir a acta salida
        return "redirect:/custodias/actaSalida";
    }

    // =========================
    // ✅ VISTA HTML ACTA ENTREGA POR CUSTODIO
    // =========================
    @GetMapping("/acta-entrega/custodio/{idCustodio}")
    public String verActaEntregaPorCustodio(@PathVariable Integer idCustodio, Model model) {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getFkCustodio() != null && x.getFkCustodio().getIdCustodio() == idCustodio)
                .toList();

        CustodiasResponseDTO cabecera = (!lista.isEmpty()) ? lista.get(0) : null;

        model.addAttribute("cabecera", cabecera);
        model.addAttribute("detalles", lista);

        return "custodias/actaEntrega";
    }

    // =========================
    // ✅ PDF ACTA ENTREGA POR CUSTODIO (SIN SESIÓN)
    // =========================
    @GetMapping("/acta-entrega/pdf/custodio/{idCustodio}")
    public void descargarActaEntregaPdfPorCustodio(
            @PathVariable Integer idCustodio,
            HttpServletResponse response) throws IOException {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getFkCustodio() != null
                          && x.getFkCustodio().getIdCustodio() == idCustodio)
                .toList();

        if (lista == null || lista.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        CustodiasResponseDTO cab = lista.get(0);

        response.reset();
        response.setContentType("application/pdf");
        response.setHeader(
            "Content-Disposition",
            "attachment; filename=Acta_Entrega_Custodio_" + idCustodio + ".pdf"
        );

        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        Font title = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

        doc.add(new Paragraph("ACTA DE ENTREGA DE EQUIPOS", title));
        doc.add(new Paragraph(" ", normal));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        doc.add(new Paragraph("ID Custodio: " + idCustodio, normal));
        doc.add(new Paragraph("Custodio: " + cab.getFkCustodio().getNombre(), normal));
        doc.add(new Paragraph("Cédula: " + cab.getFkCustodio().getCedula(), normal));
        doc.add(new Paragraph("Fecha inicio: " +
                (cab.getFechaInicio() != null ? cab.getFechaInicio().format(fmt) : ""), normal));
        doc.add(new Paragraph("Observación: " + nvl(cab.getObservacion()), normal));
        doc.add(new Paragraph(" ", normal));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Código");
        table.addCell("Tipo");
        table.addCell("Modelo");
        table.addCell("Serial");

        Set<Integer> seen = new HashSet<>();

        for (CustodiasResponseDTO it : lista) {
            if (it.getFkEquipo() == null) {
				continue;
			}
            Integer idEq = it.getFkEquipo().getIdEquipo();
            if (!seen.add(idEq)) {
				continue;
			}

            table.addCell(String.valueOf(idEq));
            table.addCell(nvl(it.getFkEquipo().getCodigoSap()));
            table.addCell(nvl(it.getFkEquipo().getTipoEquipo()));
            table.addCell(nvl(it.getFkEquipo().getModelo()));
            table.addCell(nvl(it.getFkEquipo().getSerial()));
        }

        doc.add(table);

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Firma Custodio: ____________________________", normal));
        doc.add(new Paragraph("Firma Responsable TI: ______________________", normal));

        doc.close();
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

    // =========================
    // EDITAR (si mantienes 1 línea)
    // =========================
    @GetMapping("/editar-custodia/{id}")
    public String editarCustodia(@PathVariable Integer id, Model model) {

        CustodiasResponseDTO custodia = servicioCustodias.obtenerPorId(id);

        Integer idEquipoSel = (custodia.getFkEquipo() != null) ? custodia.getFkEquipo().getIdEquipo() : 0;
        Integer idCustodioSel = (custodia.getFkCustodio() != null) ? custodia.getFkCustodio().getIdCustodio() : 0;

        model.addAttribute("listaequipos",
                servicioEquipos.listarEquipos().stream()
                        .filter(e -> e.isEstado() || e.getIdEquipo() == idEquipoSel)
                        .toList());

        model.addAttribute("listacustodios",
                servicioCustodios.listarCustodios().stream()
                        .filter(c -> c.isEstado() || c.getIdCustodio() == idCustodioSel)
                        .toList());

        model.addAttribute("custodia", custodia);

        return "custodias/editarCustodia";
    }

    // =========================
    // GUARDAR
    // - CREAR: MULTI EQUIPOS
    // - EDITAR: 1 equipo
    // =========================
    @PostMapping
    public String guardarCustodia(@ModelAttribute CustodiasRequestDTO custodia,
                                  Model model,
                                  HttpSession session) {

        if (custodia.getFkCustodio() == null) {
            custodia.setFkCustodio(new CustodiosRequestDTO());
        }

        boolean hayErrores = false;

        if (custodia.getFechaInicio() == null) {
            model.addAttribute("errorFechaInicio", "La fecha de inicio es obligatoria");
            hayErrores = true;
        }

        if (custodia.getObservacion() == null || custodia.getObservacion().trim().isEmpty()) {
            model.addAttribute("errorObservacion", "La observación es obligatoria");
            hayErrores = true;
        }

        if (custodia.getFkCustodio().getIdCustodio() <= 0) {
            model.addAttribute("errorSeleccionCustodio", "Debe seleccionar un custodio");
            hayErrores = true;
        }

        boolean esEdicion = custodia.getIdCustodiaEquipo() > 0;

        if (!esEdicion) {
            if (custodia.getEquiposSeleccionados() == null || custodia.getEquiposSeleccionados().isEmpty()) {
                model.addAttribute("errorSeleccionEquipos", "Debe seleccionar al menos un equipo");
                hayErrores = true;
            }
        } else {
            if (custodia.getFkEquipo() == null) {
				custodia.setFkEquipo(new EquiposRequestDTO());
			}
            if (custodia.getFkEquipo().getIdEquipo() <= 0) {
                model.addAttribute("errorSeleccionEquipo", "Debe seleccionar un equipo");
                hayErrores = true;
            }
        }

        if (hayErrores) {
            model.addAttribute("listaequipos",
                    servicioEquipos.listarEquipos().stream().filter(e -> e.isEstado()).toList());
            model.addAttribute("listacustodios",
                    servicioCustodios.listarCustodios().stream().filter(c -> c.isEstado()).toList());
            model.addAttribute("custodia", custodia);
            return formularioCustodia(custodia);
        }

        if (esEdicion) {
            servicioCustodias.actualizarCustodia(custodia.getIdCustodiaEquipo(), custodia);
            return "redirect:/custodias";
        }

        // ✅ CREAR MULTI
        custodia.setEstado(true);

        List<EquiposRequestDTO> equipos = custodia.getEquiposSeleccionados().stream()
                .distinct()
                .map(id -> {
                    EquiposRequestDTO e = new EquiposRequestDTO();
                    e.setIdEquipo(id);
                    return e;
                }).toList();

        custodia.setEquipos(equipos);
        custodia.setFkEquipo(null);

        List<CustodiasResponseDTO> creados = servicioCustodias.crearCustodiaActa(custodia);

        if (creados == null || creados.isEmpty()) {
            model.addAttribute("errorGeneral", "No se pudo generar el acta. La API no devolvió detalles.");
            model.addAttribute("listaequipos",
                    servicioEquipos.listarEquipos().stream().filter(e -> e.isEstado()).toList());
            model.addAttribute("listacustodios",
                    servicioCustodios.listarCustodios().stream().filter(c -> c.isEstado()).toList());
            model.addAttribute("custodia", custodia);
            return "custodias/nuevocustodia";
        }

        session.setAttribute("ACTA_ENTREGA_RECIENTE", creados);

        return "redirect:/custodias/actaEntrega";
    }

    private String formularioCustodia(CustodiasRequestDTO dto) {
        return (dto.getIdCustodiaEquipo() > 0) ? "custodias/editarCustodia" : "custodias/nuevocustodia";
    }

    // =========================
    // ✅ PDF ACTA SALIDA (usa sesión - SOLO DEVUELTOS)
    // =========================
    @GetMapping("/acta-salida/pdf")
    public void descargarActaSalidaPdf(HttpSession session, HttpServletResponse response) throws IOException {

        @SuppressWarnings("unchecked")
        List<CustodiasResponseDTO> lista =
                (List<CustodiasResponseDTO>) session.getAttribute("ACTA_SALIDA_RECIENTE");

        if (lista == null || lista.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        CustodiasResponseDTO cab = lista.get(0);

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

        String nombre = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getNombre()) : "";
        String cedula = (cab.getFkCustodio() != null) ? nvl(cab.getFkCustodio().getCedula()) : "";
        int idCustodio = (cab.getFkCustodio() != null) ? cab.getFkCustodio().getIdCustodio() : cab.getIdCustodio();

        doc.add(new Paragraph("ID Custodio: " + idCustodio, normal));
        doc.add(new Paragraph("Custodio: " + nombre, normal));
        doc.add(new Paragraph("Cédula: " + cedula, normal));
        doc.add(new Paragraph("Fecha salida: " + LocalDate.now().format(fmt), normal));
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
            if (it.getFkEquipo() == null) {
				continue;
			}
            Integer idEq = it.getFkEquipo().getIdEquipo();
            if ((idEq == null) || !seen.add(idEq)) {
				continue;
			}

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

    // =========================
    // ✅ Vista HTML del Acta (lee la sesión)
    // =========================
    @GetMapping("/actaEntrega")
    public String verActaEntrega(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CustodiasResponseDTO> lista =
                (List<CustodiasResponseDTO>) session.getAttribute("ACTA_ENTREGA_RECIENTE");

        CustodiasResponseDTO cabecera = (lista != null && !lista.isEmpty()) ? lista.get(0) : null;

        model.addAttribute("cabecera", cabecera);
        model.addAttribute("detalles", lista);

        return "custodias/actaEntrega";
    }

    @GetMapping("/actaSalida")
    public String verActaSalida(Model model, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<CustodiasResponseDTO> lista =
                (List<CustodiasResponseDTO>) session.getAttribute("ACTA_SALIDA_RECIENTE");

        CustodiasResponseDTO cabecera = (lista != null && !lista.isEmpty()) ? lista.get(0) : null;

        model.addAttribute("cabecera", cabecera);
        model.addAttribute("detalles", lista);

        return "Custodias/actaSalida";
    }

    @GetMapping("/acta-entrega/descargar-y-volver")
    public String descargarYVolver() {
        return "redirect:/custodias";
    }

    @GetMapping("/acta-entrega/pdf")
    public void descargarActaEntregaPdf(HttpSession session, HttpServletResponse response) throws IOException {

        @SuppressWarnings("unchecked")
        List<CustodiasResponseDTO> lista =
                (List<CustodiasResponseDTO>) session.getAttribute("ACTA_ENTREGA_RECIENTE");

        if (lista == null || lista.isEmpty()) {
            response.sendRedirect("/custodias");
            return;
        }

        CustodiasResponseDTO cab = lista.get(0);

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
        int idCustodio = (cab.getFkCustodio() != null) ? cab.getFkCustodio().getIdCustodio() : cab.getIdCustodio();

        doc.add(new Paragraph("ID Custodio: " + idCustodio, normal));
        doc.add(new Paragraph("Custodio: " + nombre, normal));
        doc.add(new Paragraph("Cédula: " + cedula, normal));
        doc.add(new Paragraph("Fecha inicio: " + (cab.getFechaInicio() != null ? cab.getFechaInicio().format(fmt) : ""), normal));
        doc.add(new Paragraph("Fecha fin: " + (cab.getFechaFin() != null ? cab.getFechaFin().format(fmt) : ""), normal));
        doc.add(new Paragraph("Observación: " + nvl(cab.getObservacion()), normal));
        doc.add(new Paragraph(" ", normal));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{10f, 20f, 15f, 35f, 20f});
        } catch (Exception ignored) {}

        table.addCell(headerCell("ID Equipo"));
        table.addCell(headerCell("Código"));
        table.addCell(headerCell("Tipo"));
        table.addCell(headerCell("Modelo"));
        table.addCell(headerCell("Serial"));

        Set<Integer> seen = new HashSet<>();

        for (CustodiasResponseDTO it : lista) {
            if (it.getFkEquipo() == null) {
				continue;
			}
            Integer idEq = it.getFkEquipo().getIdEquipo();
            if ((idEq == null) || !seen.add(idEq)) {
				continue;
			}

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

    // =========================
    // HELPERS PDF
    // =========================
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

    // =========================
    // ELIMINAR / ACTIVAR
    // =========================
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
}
