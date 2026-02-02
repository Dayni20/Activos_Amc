package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.EquiposRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.EquiposResponseDTO;
import com.uisrael.consumogestionactivosapi.service.IEquiposServicio;

@Service
public class EquiposServicioImpl implements IEquiposServicio {

	private final WebClient clienteWeb;

	public EquiposServicioImpl(WebClient clienteWeb) {
		this.clienteWeb = clienteWeb;
	}

	@Override
	public List<EquiposResponseDTO> listarEquipos() {
		return clienteWeb.get().uri("/equipos").retrieve().bodyToFlux(EquiposResponseDTO.class).collectList().block();
	}

	@Override
	public void crearEquipo(EquiposRequestDTO dto) {
		clienteWeb.post().uri("/equipos").bodyValue(dto).retrieve().toBodilessEntity().block();
	}

	@Override
	public EquiposResponseDTO obtenerPorId(Integer idEquipo) {
		try {
			return clienteWeb.get().uri(uriBuilder -> uriBuilder.path("/equipos/{id}").build(idEquipo)).retrieve()
					.bodyToMono(EquiposResponseDTO.class).block();
		} catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new RuntimeException("Equipo no encontrado con id: " + idEquipo);
			}
			throw e;
		}
	}

	@Override
	public void actualizarEquipo(Integer idEquipo, EquiposRequestDTO dto) {
		clienteWeb.put().uri(uriBuilder -> uriBuilder.path("/equipos/{id}").build(idEquipo)).bodyValue(dto).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarEstado(Integer idEquipo, boolean estado) {
		EquiposRequestDTO dto = new EquiposRequestDTO();
		dto.setEstado(estado);

		clienteWeb.put().uri("/equipos/estado/{id}", idEquipo).bodyValue(dto).retrieve().toBodilessEntity().block();
	}
}
