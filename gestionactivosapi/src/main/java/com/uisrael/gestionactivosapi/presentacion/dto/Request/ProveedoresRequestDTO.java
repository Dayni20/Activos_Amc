package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedoresRequestDTO {

    private Integer idProveedor;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 20)
    private String ruc;

    @NotBlank
    @Size(max = 20)
    private String telefono;

    @NotBlank
    @Size(max = 100)
    private String correo;

    @NotBlank
    @Size(max = 200)
    private String direccion;

    private boolean estado; // Ej: ACTIVO / INACTIVO
}
