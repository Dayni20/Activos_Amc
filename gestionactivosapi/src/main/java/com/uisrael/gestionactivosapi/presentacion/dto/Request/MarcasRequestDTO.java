package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MarcasRequestDTO {
	
	@NotBlank
	private int idMarca;
	@NotBlank
	private String nombre;


}
