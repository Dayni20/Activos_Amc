package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.EquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiasResponseDTO;
import com.uisrael.consumogestionactivosapi.security.SesionUsuario;
import com.uisrael.consumogestionactivosapi.service.ICustodiasServicio;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;
import com.uisrael.consumogestionactivosapi.service.IEquiposServicio;

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
        lista.sort(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo));
        model.addAttribute("listacustodias", lista);
        return "custodias/listarCustodias";
    }

    // =========================
    // FORM NUEVA CUSTODIA (MULTI EQUIPOS)
    // =========================
    @GetMapping("/nueva-custodia")
    public String nuevaCustodia(Model model) {

        CustodiasRequestDTO custodia = new CustodiasRequestDTO();
        custodia.setEstado(true);

        // fkCustodio (select)
        custodia.setFkCustodio(new CustodiosRequestDTO());
        custodia.getFkCustodio().setIdCustodio(0);

        // NOTA: ya no usamos fkEquipo para crear, porque será múltiple
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
    // EDITAR (mantienes 1 línea si quieres)
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
    // - CREAR: MULTI EQUIPOS (equiposSeleccionados -> equipos[])
    // - EDITAR: mantiene tu lógica existente por idCustodiaEquipo
    // =========================
    @PostMapping
    public String guardarCustodia(@ModelAttribute CustodiasRequestDTO custodia, Model model) {

        if (custodia.getFkCustodio() == null) custodia.setFkCustodio(new CustodiosRequestDTO());

        boolean hayErrores = false;

        // Fecha obligatoria
        if (custodia.getFechaInicio() == null) {
            model.addAttribute("errorFechaInicio", "La fecha de inicio es obligatoria");
            hayErrores = true;
        }

        // Observación obligatoria
        if (custodia.getObservacion() == null || custodia.getObservacion().trim().isEmpty()) {
            model.addAttribute("errorObservacion", "La observación es obligatoria");
            hayErrores = true;
        }

        // Custodio obligatorio
        if (custodia.getFkCustodio().getIdCustodio() <= 0) {
            model.addAttribute("errorSeleccionCustodio", "Debe seleccionar un custodio");
            hayErrores = true;
        }

        // SI ES CREACIÓN (idCustodiaEquipo = 0) => valida múltiples equipos
        boolean esEdicion = custodia.getIdCustodiaEquipo() > 0;

        if (!esEdicion) {
            if (custodia.getEquiposSeleccionados() == null || custodia.getEquiposSeleccionados().isEmpty()) {
                model.addAttribute("errorSeleccionEquipos", "Debe seleccionar al menos un equipo");
                hayErrores = true;
            }
        } else {
            // EDICIÓN: si mantienes edición 1 equipo
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

        // =========================
        // GUARDAR
        // =========================
        if (esEdicion) {
            // tu lógica original
            servicioCustodias.actualizarCustodia(custodia.getIdCustodiaEquipo(), custodia);
            return "redirect:/custodias";
        }

        // ✅ CREAR MULTI-EQUIPOS
        custodia.setEstado(true);

        // construir equipos:[{idEquipo:..},..]
        List<EquiposRequestDTO> equipos = custodia.getEquiposSeleccionados().stream()
                .map(id -> {
                    EquiposRequestDTO e = new EquiposRequestDTO();
                    e.setIdEquipo(id);
                    return e;
                })
                .toList();

        custodia.setEquipos(equipos);

        // NO enviar fkEquipo en creación
        custodia.setFkEquipo(null);

        // Llama a la API (tu Postman devuelve lista)
        List<CustodiasResponseDTO> creados = servicioCustodias.crearCustodiaActa(custodia);

        // Tomamos idCustodia del primer registro para “acta”
        int idCustodiaActa = (creados != null && !creados.isEmpty()) ? creados.get(0).getIdCustodia() : 0;

        return "redirect:/custodias/acta-entrega/" + idCustodiaActa;
    }

    private String formularioCustodia(CustodiasRequestDTO dto) {
        return (dto.getIdCustodiaEquipo() > 0) ? "custodias/editarCustodia" : "custodias/nuevocustodia";
    }

    // =========================
    // ACTA ENTREGA (por ahora solo pantalla para probar)
    // =========================
    @GetMapping("/acta-entrega/{idCustodia}")
    public String verActaEntrega(@PathVariable Integer idCustodia, Model model) {

        // si tu API no tiene endpoint por idCustodia, filtramos desde listarCustodias()
        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias().stream()
                .filter(x -> x.getIdCustodia() == idCustodia)
                .toList();

        if (lista.isEmpty()) return "redirect:/custodias";

        model.addAttribute("cabecera", lista.get(0));
        model.addAttribute("detalles", lista);

        return "custodias/actaEntrega"; // crea esta vista simple para ver que sí generó
    }

    // =========================
    // ELIMINAR / ACTIVAR (tu lógica)
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
