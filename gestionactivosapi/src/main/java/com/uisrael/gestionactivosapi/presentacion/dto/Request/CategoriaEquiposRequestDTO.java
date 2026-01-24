package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaEquiposRequestDTO {
	
	private int idCategoria;
	
	@NotBlank
	private String nombre;
	
	private boolean estado;

}
