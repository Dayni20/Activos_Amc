package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuariosRequestDTO {
	
	private int idUsuario;
	
	@NotBlank(message = "El nombre es obligatorio")
	@Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
	@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
	private String nombre;
	
	@NotBlank(message = "El correo es obligatorio")
	@Email(message = "El correo debe tener un formato válido")
	private String correo;
	
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "La contraseña debe contener al menos una mayúscula, una minúscula y un número")
	private String contrasena;
	
	private boolean estado;
    
	private DepartamentosRequestDTO fkDepartamento;

	private RolesRequestDTO fkRol;

}
