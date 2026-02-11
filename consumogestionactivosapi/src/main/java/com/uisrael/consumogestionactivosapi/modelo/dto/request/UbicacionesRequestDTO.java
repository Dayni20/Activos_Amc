package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import lombok.Data;

@Data
public class UbicacionesRequestDTO {

	private int idUbicacion;
	private String nombre;
	private String agencia;
	private boolean estado;

}
