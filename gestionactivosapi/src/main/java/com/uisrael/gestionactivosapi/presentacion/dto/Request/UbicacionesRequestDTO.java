package com.uisrael.gestionactivosapi.presentacion.dto.Request;


import lombok.Data;

@Data
public class UbicacionesRequestDTO {

	private int idUbicacion;
	private String nombre;
	private String agencia;

	private boolean estado;

}
