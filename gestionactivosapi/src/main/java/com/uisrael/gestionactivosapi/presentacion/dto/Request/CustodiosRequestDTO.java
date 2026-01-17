package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustodiosRequestDTO {

    @NotBlank
    private int idCustodio;

    @NotBlank
    private String nombre;

    @NotBlank
    private String cedula;

    @NotBlank
    private String correo;

    @NotBlank
    private String telefono;

    @NotBlank
    private String estado;
}
