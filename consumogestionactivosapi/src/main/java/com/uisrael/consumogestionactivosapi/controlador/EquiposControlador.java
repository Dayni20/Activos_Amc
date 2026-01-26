package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipos") // url
public class EquiposControlador {

    @GetMapping
    public String listarEquipos() {
        return "equipos/listarEquipos"; // ubicacion fisica page
    }

    @GetMapping("/nuevo-equipo")
    public String nuevoEquipo() {
        return "equipos/nuevoEquipo"; // ubicacion fisica page
    }

    @GetMapping("/editar-equipo")
    public String modificarEquipo() {
        return "equipos/editarEquipo"; // ubicacion fisica page
    }
}
