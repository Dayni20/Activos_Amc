package com.uisrael.consumogestionactivosapi.modelo.dto.request;

import lombok.Data;

@Data
public class UsuariosRequestDTO {
	private int idUsuario;
	private String nombre;
	private String correo;
	private String contrasena;
	private boolean estado;
	private Integer fkDepartamento;
	private Integer fkRol;
}
