package com.uisrael.gestionactivosapi.aplicacion.excepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> manejarValidacionNegocio(IllegalArgumentException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> manejarErroresGenerales(RuntimeException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> manejarExcepcionesGenerales(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Error interno del servidor: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
