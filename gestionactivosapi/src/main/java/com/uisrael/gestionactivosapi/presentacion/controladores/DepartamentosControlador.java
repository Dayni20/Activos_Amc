package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IDepartamentosUseCase;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.DepartamentosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.DepartamentosResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IDepartamentosDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentosControlador {
	
	private final IDepartamentosUseCase departamentoUseCase;
	
	private final IDepartamentosDtoMapper mapper;

	public DepartamentosControlador(IDepartamentosUseCase departamentoUseCase, IDepartamentosDtoMapper mapper) {
		this.departamentoUseCase = departamentoUseCase;
		this.mapper = mapper;
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public DepartamentosResponseDTO crear(@Valid @RequestBody DepartamentosRequestDTO request) {
		return mapper.toResponseDto(departamentoUseCase.crear(mapper.toDomain(request)));
	}
	
	@GetMapping
	public List<DepartamentosResponseDTO> listar() {
		return departamentoUseCase.listar().stream().map(mapper::toResponseDto).toList();
		
	}
}
