package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolesRequestDTO {
	
	private int idRol;
	
	@NotBlank
	private String nombre;

}
