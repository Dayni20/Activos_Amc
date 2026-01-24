package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MarcasRequestDTO {
	
	private int idMarca;
	@NotBlank
	private String nombre;
	
	private boolean estado;


}
