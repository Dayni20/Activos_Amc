package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolesRequestDTO {
	
	private int id_rol;
	
	@NotBlank
	private String nombre;

}
