package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.MarcasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.MarcasResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IMarcasServicio;

@Service
public class MarcasServicioImpl  implements IMarcasServicio{
	
	private final WebClient clienteweb;
	
	public MarcasServicioImpl(WebClient clienteweb) {
		super();
		this.clienteweb = clienteweb;
	}

	@Override
	public List<MarcasResponseDTO> listarMarca() {
		return clienteweb.get().uri("/marcas").retrieve().bodyToFlux(MarcasResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevaMarca(MarcasRequestDTO dto) {
		clienteweb.post().uri("/marcas/nuevaMarcas").bodyValue(dto).retrieve().toBodilessEntity().block();
		
	}

	@Override
	public MarcasResponseDTO obtenerMarca(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarMarca(Integer id, MarcasRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarMarca(Integer id) {
		// TODO Auto-generated method stub
		
	}
	

}
