package com.uisrael.consumogestionactivosapi.modelo.dto.response;

import lombok.Data;

@Data
public class UsuariosResponseDTO {
	private int idUsuario;
	private String nombre;
	private String correo;
	private boolean estado;
	private Integer fkDepartamento;
	private RolesResponseDTO fkRol;
}
