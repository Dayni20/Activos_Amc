package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.UsuariosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.UsuariosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IUsuariosServicio;

@Service
public class UsuariosServicioImpl implements IUsuariosServicio {
	
	private final WebClient clienteweb;
	
	public UsuariosServicioImpl(WebClient clienteweb) {
		super();
		this.clienteweb = clienteweb;
	}

	@Override
	public List<UsuariosResponseDTO> listarUsuario() {
		return clienteweb.get().uri("/usuarios").retrieve().bodyToFlux(UsuariosResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevoUsuario(UsuariosRequestDTO dto) {
		clienteweb.post().uri("/usuarios").bodyValue(dto).retrieve().toBodilessEntity().block();
	}

	@Override
	public UsuariosResponseDTO obtenerUsuario(Integer id) {
		return null;
	}

	@Override
	public void actualizarUsuario(Integer id, UsuariosRequestDTO dto) {
		
	}

	@Override
	public void eliminarUsuario(Integer id) {
		
	}
}
