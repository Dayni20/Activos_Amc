package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import lombok.Data;

@Data
public class RolesRequestDTO {
	private int idRol;
	private String nombre;
	private boolean estado;
}
