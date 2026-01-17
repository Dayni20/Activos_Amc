package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuariosRequestDTO {
	
	private int idUsuario;
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	@Email
	private String correo;
	
	@NotBlank
	private String contrasena;
	
	private String estado;
	
	private DepartamentosRequestDTO fkDepartamento;

}
