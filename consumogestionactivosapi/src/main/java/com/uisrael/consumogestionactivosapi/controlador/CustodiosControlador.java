package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custodios") // url
public class CustodiosControlador {

    @GetMapping
    public String listarCustodios() {
        return "custodios/listarCustodios"; // ubicacion fisica page
    }

    @GetMapping("/nuevo-custodio")
    public String nuevoCustodio() {
        return "custodios/nuevoCustodio"; // ubicacion fisica page
    }

    @GetMapping("/editar-custodio")
    public String modificarCustodio() {
        return "custodios/editarCustodio"; // ubicacion fisica page
    }
}
