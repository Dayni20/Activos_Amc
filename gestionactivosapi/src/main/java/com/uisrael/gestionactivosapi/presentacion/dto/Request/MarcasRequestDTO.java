package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MarcasRequestDTO {
	
	private int idMarca;
	private String nombre;
	
	private boolean estado;


}
