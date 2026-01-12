package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustodiasRequestDTO {

    @NotBlank
    private int id_custodia_equipo;

    @NotBlank
    private int id_equipo;

    @NotBlank
    private int id_custodio;

    @NotBlank
    private LocalDate fecha_inicio;

    @NotBlank
    private LocalDate fecha_fin;

    @NotBlank
    private String observacion;
}
