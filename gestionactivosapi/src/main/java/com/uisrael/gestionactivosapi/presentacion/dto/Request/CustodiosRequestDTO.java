package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustodiosRequestDTO {

    private int idCustodio;

    @NotBlank
    private String nombre;

    @NotBlank
    private String cedula;

    private String correo;
    private String telefono;

    private boolean estado;
}
