package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUbicacionesUseCase;
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
}
