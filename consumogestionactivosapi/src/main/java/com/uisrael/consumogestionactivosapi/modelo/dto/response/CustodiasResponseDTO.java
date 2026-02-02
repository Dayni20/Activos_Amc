package com.uisrael.consumogestionactivosapi.modelo.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustodiasResponseDTO {
    private int idCustodiaEquipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observacion;
    private boolean estado;

    private EquiposResponseDTO fkEquipo;
    private CustodiosResponseDTO fkCustodio;
}
