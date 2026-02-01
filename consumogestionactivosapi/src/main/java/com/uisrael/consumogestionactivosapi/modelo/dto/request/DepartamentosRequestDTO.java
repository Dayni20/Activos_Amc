package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import lombok.Data;

@Data
public class DepartamentosRequestDTO {
	
	private int idDepartamento;
	private String nombre;
	private boolean estado;
	private UbicacionesRequestDTO fkUbicacion;

}
