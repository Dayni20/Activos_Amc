package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import lombok.Data;

@Data
public class CustodiosRequestDTO {

    private int idCustodio;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private boolean estado;
}
