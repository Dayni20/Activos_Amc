package com.uisrael.consumogestionactivosapi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custodias") // url
public class CustodiasControlador {

    @GetMapping
    public String listarCustodias() {
        return "custodias/listarCustodias"; // ubicacion fisica page
    }

    @GetMapping("/nuevo-custodia")
    public String nuevoCustodia() {
        return "custodias/nuevoCustodia"; // ubicacion fisica page
    }

    @GetMapping("/editar-custodia")
    public String modificarCustodia() {
        return "custodias/editarCustodia"; // ubicacion fisica page
    }
}
