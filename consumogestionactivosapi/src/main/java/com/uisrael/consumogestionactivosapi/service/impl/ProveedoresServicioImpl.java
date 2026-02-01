package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.ProveedoresRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.ProveedoresResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IProveedoresServicio;

@Service
public class ProveedoresServicioImpl implements IProveedoresServicio {
	
	private final WebClient clienteweb;
	

	public ProveedoresServicioImpl(WebClient clienteweb) {
		super();
		this.clienteweb = clienteweb;
	}

	@Override
	public List<ProveedoresResponseDTO> listarProveedores() {
		return clienteweb.get().uri("/proveedores").retrieve().bodyToFlux(ProveedoresResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevoProveedores(ProveedoresRequestDTO dto) {
		clienteweb.post().uri("/proveedores").bodyValue(dto).retrieve().toBodilessEntity().block();
		
	}

	@Override
	public ProveedoresResponseDTO obtenerProveedor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarProveedor(Integer id, ProveedoresRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarProveedor(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	

}
