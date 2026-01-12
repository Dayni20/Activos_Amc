package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuariosRequestDTO {
	
	private int id_usuario;
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	@Email
	private String correo;
	
	@NotBlank
	private String contrasena;
	
	@NotNull
	private Integer id_rol;
	
	@NotNull
	private Integer id_departamento;
	
	private String estado;

}
