package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

        model.addAttribute("listadepartamentos",
            servicioDepartamentos.listarDepartamentos().stream()
                .filter(d -> d.isEstado() || d.getIdDepartamento() == idDepSel)
                .collect(Collectors.toList())
        );

        model.addAttribute("listamarcas",
            servicioMarcas.listarMarca().stream() 
                .filter(m -> m.isEstado() || m.getIdMarca() == idMarcaSel)
                .collect(Collectors.toList())
        );

        model.addAttribute("listaproveedores",
            servicioProveedores.listarProveedores().stream()
                .filter(p -> p.isEstado() || p.getIdProveedor() == idProvSel)
                .collect(Collectors.toList())
        );

        model.addAttribute("listacategorias",
            servicioCategoriaEquipos.listarCategoriaEquipo().stream() // ✅ tu método real
                .filter(c -> c.isEstado() || c.getIdCategoria() == idCatSel)
                .collect(Collectors.toList())
        );
    }
}
