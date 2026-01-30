package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedoresRequestDTO {

    private Integer idProveedor;

    @Size(max = 100)
    private String nombre;
   
    @Size(max = 20)
    private String ruc;
    
    @Size(max = 20)
    private String telefono;
   
    @Size(max = 100)
    private String correo;

    @Size(max = 200)
    private String direccion;

    private boolean estado; // Ej: ACTIVO / INACTIVO
}
