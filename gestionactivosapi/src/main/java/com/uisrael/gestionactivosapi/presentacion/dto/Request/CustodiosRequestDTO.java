package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.time.LocalDate;

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
    
    private LocalDate fechaIngreso;

    private boolean estado;
    
    private DepartamentosRequestDTO fkDepartamento;
    
    private CargosRequestDTO fkCargo;
}
