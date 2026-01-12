package com.uisrael.gestionactivosapi.presentacion.dto.Response;

import lombok.Data;

@Data
public class UsuariosResponseDTO {
	
	private int id_usuario;
	private String nombre;
	private String correo;
	private int id_rol;
	private int id_departamento;
	private String estado;

}
