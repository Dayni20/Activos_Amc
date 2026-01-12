package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartamentosRequestDTO {
	
	@NotBlank
	private int id_departamento;
	@NotBlank
	private String nombre;
	@NotBlank
	private boolean estado;

}
