package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUbicacionesUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.UbicacionesRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.UbicacionesResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IUbicacionesDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionesControlador {
	
	private final IUbicacionesUseCase ubicacionUseCase;
	
	private final IUbicacionesDtoMapper mapper;

	public UbicacionesControlador(IUbicacionesUseCase ubicacionUseCase, IUbicacionesDtoMapper mapper) {
		this.ubicacionUseCase = ubicacionUseCase;
		this.mapper = mapper;
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UbicacionesResponseDTO crear(@Valid @RequestBody UbicacionesRequestDTO request) {
		return mapper.toResponseDto(ubicacionUseCase.crear(mapper.toDomain(request)));
	}
	
	@GetMapping
	public List<UbicacionesResponseDTO> listar() {
		return ubicacionUseCase.listar().stream().map(mapper::toResponseDto).toList();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UbicacionesResponseDTO> actualizar(@PathVariable int id,
			@Valid @RequestBody UbicacionesRequestDTO request) {

		Ubicaciones actualizado = ubicacionUseCase.actualizar(id, mapper.toDomain(request));
		return ResponseEntity.ok(mapper.toResponseDto(actualizado));
	}
	
	@PutMapping("/estado/{id}")
	public ResponseEntity<UbicacionesResponseDTO> actualizarEstado(@PathVariable int id,
			@Valid @RequestBody UbicacionesRequestDTO request) {

		Ubicaciones actualizadoEstado = ubicacionUseCase.actualizarEstado(id, mapper.toDomain(request));
		return ResponseEntity.ok(mapper.toResponseDto(actualizadoEstado));
	}
}
