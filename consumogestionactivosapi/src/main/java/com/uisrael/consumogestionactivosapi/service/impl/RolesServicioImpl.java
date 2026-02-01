package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.RolesRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.RolesResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IRolesServicio;

@Service
public class RolesServicioImpl implements IRolesServicio {
	
	private final WebClient clienteweb;
	
	public RolesServicioImpl(WebClient clienteweb) {
		super();
		this.clienteweb = clienteweb;
	}

	@Override
	public List<RolesResponseDTO> listarRol() {
		return clienteweb.get().uri("/roles").retrieve().bodyToFlux(RolesResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevoRol(RolesRequestDTO dto) {
		clienteweb.post().uri("/roles").bodyValue(dto).retrieve().toBodilessEntity().block();
	}

	@Override
	public RolesResponseDTO obtenerRol(Integer id) {
		return null;
	}

	@Override
	public void actualizarRol(Integer id, RolesRequestDTO dto) {
		
	}

	@Override
	public void eliminarRol(Integer id) {
		
	}
}
