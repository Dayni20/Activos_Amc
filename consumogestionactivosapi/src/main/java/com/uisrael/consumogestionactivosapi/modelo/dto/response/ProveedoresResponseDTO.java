package com.uisrael.consumogestionactivosapi.modelo.dto.response;

import lombok.Data;

@Data
public class ProveedoresResponseDTO {

	private int idProveedor;
	private String nombre;
	private String ruc;
	private String telefono;
	private String correo;
	private String direccion;
	private boolean estado;


}
