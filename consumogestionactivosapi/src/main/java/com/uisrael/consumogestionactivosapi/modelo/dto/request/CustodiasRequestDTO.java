package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class CustodiasRequestDTO {

    // CABECERA (ACTA)
    private int idCustodia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observacion;
    private boolean estado;

    private CustodiosRequestDTO fkCustodio;

    // CREAR: selección por checkbox
    private List<Integer> equiposSeleccionados;
    private List<EquiposRequestDTO> equipos;

    // DETALLE (si mantienes edición 1 línea)
    private int idCustodiaEquipo;
    private EquiposRequestDTO fkEquipo;

    // CIERRE: checklist devolución (id detalle entregado)
    private List<Integer> detallesEntregados;
}