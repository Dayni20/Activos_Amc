package com.uisrael.consumogestionactivosapi.modelo.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustodiasResponseDTO {

    // ✅ CABECERA (ACTA)
    private int idCustodia;   // <-- NUEVO (id custodia / acta)
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observacion;
    private boolean estado;

    private CustodiosResponseDTO fkCustodio;

    // ✅ DETALLE
    private int idCustodiaEquipo;
    private EquiposResponseDTO fkEquipo;

    // (Opcional: si tu API luego devuelve entregado)
    private Boolean entregado;
    private String observacionDevolucion;
}