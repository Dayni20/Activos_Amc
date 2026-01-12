package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaEquiposRequestDTO {
	
	private int id_categoria;
	
	@NotBlank
	private String nombre;

}
