package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustodiasRequestDTO {

    @NotBlank
    private int idCustodiaEquipo;

    @NotBlank
    private LocalDate fechaInicio;

    @NotBlank
    private LocalDate fechaFin;

    @NotBlank
    private String observacion;
}
