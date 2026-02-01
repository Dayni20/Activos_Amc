package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CategoriaEquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CategoriaEquiposResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICategoriaEquiposServicio;

@Service
public class CategoriaEquiposServicioImpl implements ICategoriaEquiposServicio {
	
	private final WebClient clienteweb;
	
	public CategoriaEquiposServicioImpl(WebClient clienteweb) {
		super();
		this.clienteweb = clienteweb;
	}

	@Override
	public List<CategoriaEquiposResponseDTO> listarCategoriaEquipo() {
		return clienteweb.get().uri("/categoriaequipos").retrieve().bodyToFlux(CategoriaEquiposResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevoCategoriaEquipo(CategoriaEquiposRequestDTO dto) {
		clienteweb.post().uri("/categoriaequipos").bodyValue(dto).retrieve().toBodilessEntity().block();
	}

	@Override
	public CategoriaEquiposResponseDTO obtenerCategoriaEquipo(Integer id) {
		return null;
	}

	@Override
	public void actualizarCategoriaEquipo(Integer id, CategoriaEquiposRequestDTO dto) {
		
	}

	@Override
	public void eliminarCategoriaEquipo(Integer id) {
		
	}
}
