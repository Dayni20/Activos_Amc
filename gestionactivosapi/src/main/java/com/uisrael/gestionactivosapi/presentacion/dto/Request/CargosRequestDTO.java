package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import lombok.Data;

@Data
public class CargosRequestDTO {

	private int idCargo;
	private String nombre;
	private boolean estado;
}
