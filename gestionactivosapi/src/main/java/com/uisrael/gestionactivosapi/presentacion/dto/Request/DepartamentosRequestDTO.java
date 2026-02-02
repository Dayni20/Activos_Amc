package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartamentosRequestDTO {
	
	private int idDepartamento;
	private String nombre;

	private boolean estado;
	
	private UbicacionesRequestDTO fkUbicacion;

}
