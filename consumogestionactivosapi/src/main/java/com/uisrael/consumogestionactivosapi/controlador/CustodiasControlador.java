package com.uisrael.consumogestionactivosapi.controlador;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // =========================
    // LISTAR
    // =========================
    @GetMapping
    public String listarCustodias(Model model) {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias();

        // ✅ ordena por custodio para que tu agrupación en thymeleaf funcione
        lista.sort(
                Comparator.comparing((CustodiasResponseDTO x) -> x.getFkCustodio().getIdCustodio())
                          .thenComparing(CustodiasResponseDTO::getIdCustodiaEquipo)
        );

        model.addAttribute("listacustodias", lista);
        return "custodias/listarCustodias";
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
    public void descargarActaEntregaPdfPorCustodio(@PathVariable Integer idCustodio,
                                                   HttpServletResponse response) throws IOException {

        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getFkCustodio() != null && x.getFkCustodio().getIdCustodio() == idCustodio)
                .toList();

        if (lista == null || lista.isEmpty()) {
            response.sendRedirect("/custodias");
            return;
        }

        CustodiasResponseDTO cab = lista.get(0);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=Acta_Entrega_Custodio_" + idCustodio + ".pdf");

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
            if (it.getFkEquipo() == null) continue;
            Integer idEq = it.getFkEquipo().getIdEquipo();
            if (idEq == null) continue;
            if (!seen.add(idEq)) continue;

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
            if (custodia.getFkEquipo() == null) custodia.setFkEquipo(new EquiposRequestDTO());
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

        // ✅ guardamos lo recién creado en sesión
        session.setAttribute("ACTA_ENTREGA_RECIENTE", creados);

        // ✅ vamos a una vista que renderiza el acta (HTML)
        return "redirect:/custodias/actaEntrega";
    }

    private String formularioCustodia(CustodiasRequestDTO dto) {
        return (dto.getIdCustodiaEquipo() > 0) ? "custodias/editarCustodia" : "custodias/nuevocustodia";
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

    // =========================
    // ✅ "Descargar y volver" -> redirige
    // =========================
    @GetMapping("/acta-entrega/descargar-y-volver")
    public String descargarYVolver() {
        return "redirect:/custodias";
    }

    // =========================
    // ✅ PDF Acta Entrega (usa sesión)
    // =========================
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
            if (it.getFkEquipo() == null) continue;
            Integer idEq = it.getFkEquipo().getIdEquipo();
            if (idEq == null) continue;
            if (!seen.add(idEq)) continue;

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
