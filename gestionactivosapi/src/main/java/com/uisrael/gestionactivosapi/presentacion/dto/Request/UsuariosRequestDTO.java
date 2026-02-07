package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuariosRequestDTO {
	
	private int idUsuario;
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	
	@NotBlank(message = "El correo es obligatorio")
	private String correo;
	
	// No requerida en edición - puede estar vacía si no se cambia
	private String contrasena;
	
	private boolean estado;
    
	private DepartamentosRequestDTO fkDepartamento;

	private RolesRequestDTO fkRol;

}
