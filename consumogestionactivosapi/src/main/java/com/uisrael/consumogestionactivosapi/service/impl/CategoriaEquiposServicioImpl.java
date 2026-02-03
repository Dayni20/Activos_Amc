package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
		return clienteweb.get().uri("/categorias-equipo").retrieve().bodyToFlux(CategoriaEquiposResponseDTO.class).collectList().block();
	}

	@Override
	public void nuevoCategoriaEquipo(CategoriaEquiposRequestDTO dto) {
		try {
			clienteweb.post().uri("/categorias-equipo").bodyValue(dto).retrieve().toBodilessEntity().block();
		} catch (WebClientResponseException ex) {
			String errorBody = ex.getResponseBodyAsString();
			String mensaje = extraerMensajeError(errorBody);
			throw new RuntimeException(mensaje);
		}
	}

	@Override
	public CategoriaEquiposResponseDTO obtenerCategoriaEquipo(Integer id) {
		return clienteweb.get().uri("/categorias-equipo/" + id).retrieve().bodyToMono(CategoriaEquiposResponseDTO.class).block();
	}

	@Override
	public void actualizarCategoriaEquipo(Integer id, CategoriaEquiposRequestDTO dto) {
		try {
			dto.setIdCategoria(id);
			clienteweb.put().uri("/categorias-equipo").bodyValue(dto).retrieve().toBodilessEntity().block();
		} catch (WebClientResponseException ex) {
			String errorBody = ex.getResponseBodyAsString();
			String mensaje = extraerMensajeError(errorBody);
			throw new RuntimeException(mensaje);
		}
	}

	@Override
	public void eliminarCategoriaEquipo(Integer id) {
		clienteweb.delete().uri("/categorias-equipo/" + id).retrieve().toBodilessEntity().block();
	}

	private String extraerMensajeError(String errorBody) {
		try {
			int inicioMensaje = errorBody.indexOf(":") + 2;
			int finMensaje = errorBody.lastIndexOf("\"");
			return errorBody.substring(inicioMensaje, finMensaje);
		} catch (Exception e) {
			return "Error al procesar la solicitud";
		}
	}
}
