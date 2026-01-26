package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosControlador {

    @GetMapping
    public String listarUsuarios() {
        return "usuarios/listarUsuarios";
    }

    @GetMapping("/nuevo-usuario")
    public String nuevoUsuario() {
        return "usuarios/nuevoUsuario";
    }

    @GetMapping("/editar-usuario")
    public String editarUsuario() {
        return "usuarios/editarUsuario";
    }

}
