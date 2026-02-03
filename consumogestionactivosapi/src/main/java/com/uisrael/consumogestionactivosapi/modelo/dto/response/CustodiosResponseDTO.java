package com.uisrael.consumogestionactivosapi.modelo.dto.response;

import lombok.Data;

@Data
public class CustodiosResponseDTO {

    private int idCustodio;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private boolean estado;
}
