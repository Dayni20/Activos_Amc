package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RolesControlador {

    @GetMapping
    public String listarRoles() {
        return "roles/listarRoles";
    }

    @GetMapping("/nuevo-rol")
    public String nuevoRol() {
        return "roles/nuevoRol";
    }

    @GetMapping("/editar-rol")
    public String editarRol() {
        return "roles/editarRol";
    }

}
