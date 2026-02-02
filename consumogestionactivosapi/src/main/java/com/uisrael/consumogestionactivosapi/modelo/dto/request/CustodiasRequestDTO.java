package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustodiasRequestDTO {

    private int idCustodiaEquipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observacion;
    private boolean estado;

    // ✅ relaciones
    private EquiposRequestDTO fkEquipo;
    private CustodiosRequestDTO fkCustodio;
}
