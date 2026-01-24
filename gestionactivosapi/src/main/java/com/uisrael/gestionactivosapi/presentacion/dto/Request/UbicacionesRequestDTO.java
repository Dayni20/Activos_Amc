package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UbicacionesRequestDTO {
	
	private int idUbicacion;
	@NotBlank
	private String nombre;
	@NotBlank
	private String agencia;

	private boolean estado;

}
