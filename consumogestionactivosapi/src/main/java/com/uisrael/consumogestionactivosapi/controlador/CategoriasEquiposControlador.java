package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias-equipo")
public class CategoriasEquiposControlador {

    @GetMapping
    public String listarCategorias() {
        return "categorias_equipo/listarCategorias";
    }

    @GetMapping("/nueva-categoria")
    public String nuevaCategoria() {
        return "categorias_equipo/nuevaCategoria";
    }

    @GetMapping("/editar-categoria")
    public String editarCategoria() {
        return "categorias_equipo/editarCategoria";
    }

}
