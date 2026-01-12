package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UbicacionesRequestDTO {
	
	@NotBlank
	private int id_ubicacion;
	@NotBlank
	private String nombre;
	@NotBlank
	private String agencia;
	@NotBlank
	private boolean estado;

}
