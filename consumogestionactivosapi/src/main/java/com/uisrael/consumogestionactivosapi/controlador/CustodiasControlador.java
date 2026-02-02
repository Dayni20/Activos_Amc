package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.request.EquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiasResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICustodiasServicio;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;
import com.uisrael.consumogestionactivosapi.service.IEquiposServicio;

@Controller
@RequestMapping("/custodias")
public class CustodiasControlador {

    @Autowired
    private ICustodiasServicio servicioCustodias;

    @Autowired
    private IEquiposServicio servicioEquipos;

    @Autowired
    private ICustodiosServicio servicioCustodios;

    // LISTAR
    @GetMapping
    public String listarCustodias(Model model) {
        List<CustodiasResponseDTO> lista = servicioCustodias.listarCustodias();
        lista.sort(Comparator.comparing(CustodiasResponseDTO::getIdCustodiaEquipo));
        model.addAttribute("listacustodias", lista);
        return "custodias/listarCustodias";
    }

    // NUEVO
    @GetMapping("/nueva-custodia")
    public String nuevaCustodia(Model model) {

        CustodiasRequestDTO custodia = new CustodiasRequestDTO();
        custodia.setEstado(true);

        // ✅ IMPORTANTE: objetos anidados para que th:field funcione
        custodia.setFkEquipo(new EquiposRequestDTO());
        custodia.getFkEquipo().setIdEquipo(0);

        custodia.setFkCustodio(new CustodiosRequestDTO());
        custodia.getFkCustodio().setIdCustodio(0);

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

    // EDITAR (opcional pero igual que tu compañero)
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

    // GUARDAR (crea / actualiza)
    @PostMapping
    public String guardarCustodia(@ModelAttribute CustodiasRequestDTO custodia, Model model) {

        // 🔒 asegura objetos anidados
        if (custodia.getFkEquipo() == null) custodia.setFkEquipo(new EquiposRequestDTO());
        if (custodia.getFkCustodio() == null) custodia.setFkCustodio(new CustodiosRequestDTO());

        boolean hayErrores = false;

        // 1) Fecha inicio obligatoria
        if (custodia.getFechaInicio() == null) {
            model.addAttribute("errorFechaInicio", "La fecha de inicio es obligatoria");
            hayErrores = true;
        }

        // 2) Observación obligatoria
        if (custodia.getObservacion() == null || custodia.getObservacion().trim().isEmpty()) {
            model.addAttribute("errorObservacion", "La observación es obligatoria");
            hayErrores = true;
        }

        // 3) Equipo obligatorio
        if (custodia.getFkEquipo().getIdEquipo() <= 0) {
            model.addAttribute("errorSeleccionEquipo", "Debe seleccionar un equipo");
            hayErrores = true;
        }

        // 4) Custodio obligatorio
        if (custodia.getFkCustodio().getIdCustodio() <= 0) {
            model.addAttribute("errorSeleccionCustodio", "Debe seleccionar un custodio");
            hayErrores = true;
        }

        // 🔴 si hay errores, vuelve al formulario
        if (hayErrores) {
            model.addAttribute("listaequipos",
                    servicioEquipos.listarEquipos().stream().filter(e -> e.isEstado()).toList());
            model.addAttribute("listacustodios",
                    servicioCustodios.listarCustodios().stream().filter(c -> c.isEstado()).toList());
            model.addAttribute("custodia", custodia);
            return formularioCustodia(custodia);
        }

        // Guardar (create/update)
        if (custodia.getIdCustodiaEquipo() > 0) {
            servicioCustodias.actualizarCustodia(custodia.getIdCustodiaEquipo(), custodia);
        } else {
            servicioCustodias.crearCustodia(custodia);
        }

        return "redirect:/custodias";
    }

    private String formularioCustodia(CustodiasRequestDTO dto) {
        return (dto.getIdCustodiaEquipo() > 0) ? "custodias/editarCustodia" : "custodias/nuevaCustodia";
    }

    // ELIMINAR LÓGICO
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
