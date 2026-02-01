package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaEquiposRequestDTO {
	
	private int idCategoria;
	
	@NotBlank(message = "El nombre de la categoría es obligatorio")
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
	@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s]+$", message = "El nombre solo puede contener letras, números y espacios")
	private String nombre;
	
	private boolean estado;

}
