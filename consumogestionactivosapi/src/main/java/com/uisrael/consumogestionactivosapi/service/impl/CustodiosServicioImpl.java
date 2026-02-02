package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiosRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiosResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICustodiosServicio;

@Service
public class CustodiosServicioImpl implements ICustodiosServicio {

	private final WebClient webClient;

	public CustodiosServicioImpl(WebClient.Builder builder) {
		this.webClient = builder.baseUrl("http://localhost:8080/api") // API real
				.build();
	}

	@Override
	public List<CustodiosResponseDTO> listarCustodios() {
		CustodiosResponseDTO[] arr = webClient.get().uri("/custodios").retrieve()
				.bodyToMono(CustodiosResponseDTO[].class).block();

		return arr == null ? List.of() : Arrays.asList(arr);
	}

	@Override
	public CustodiosResponseDTO obtenerPorId(Integer idCustodio) {
		return webClient.get().uri("/custodios/{id}", idCustodio).retrieve().bodyToMono(CustodiosResponseDTO.class)
				.block();
	}

	@Override
	public void crearCustodio(CustodiosRequestDTO dto) {
		webClient.post().uri("/custodios").contentType(MediaType.APPLICATION_JSON).bodyValue(dto).retrieve()
				.bodyToMono(Void.class).block();
	}

	@Override
	public void actualizarCustodio(Integer idCustodio, CustodiosRequestDTO dto) {
		webClient.put().uri("/custodios/{id}", idCustodio).contentType(MediaType.APPLICATION_JSON).bodyValue(dto)
				.retrieve().bodyToMono(Void.class).block();
	}

	@Override
	public void actualizarEstado(Integer idCustodio, boolean estado) {

		// 1) Traer datos actuales (para no violar @NotBlank de la API)
		CustodiosResponseDTO actual = obtenerPorId(idCustodio);

		if (actual == null) {
			throw new RuntimeException("No existe el custodio con id: " + idCustodio);
		}

		// 2) Armar RequestDTO completo, cambiando solo estado
		CustodiosRequestDTO body = new CustodiosRequestDTO();
		body.setIdCustodio(actual.getIdCustodio());
		body.setNombre(actual.getNombre());
		body.setCedula(actual.getCedula());
		body.setCorreo(actual.getCorreo());
		body.setTelefono(actual.getTelefono());
		body.setEstado(estado);

		// 3) PUT /estado/{id} con TODO el body
		webClient.put().uri("/custodios/estado/{id}", idCustodio).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(body).retrieve().bodyToMono(Void.class).block();
	}
}
