package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiasResponseDTO;
import com.uisrael.consumogestionactivosapi.service.ICustodiasServicio;

@Service
public class CustodiasServicioImpl implements ICustodiasServicio {

	private final WebClient clienteWeb;

	public CustodiasServicioImpl(WebClient clienteWeb) {
		this.clienteWeb = clienteWeb;
	}

	@Override
	public List<CustodiasResponseDTO> listarCustodias() {
		return clienteWeb.get().uri("/custodias").retrieve().bodyToFlux(CustodiasResponseDTO.class).collectList()
				.block();
	}

	@Override
	public void crearCustodia(CustodiasRequestDTO dto) {
		clienteWeb.post().uri("/custodias").bodyValue(dto).retrieve().toBodilessEntity().block();
	}

	@Override
	public CustodiasResponseDTO obtenerPorId(Integer idCustodiaEquipo) {
		try {
			return clienteWeb.get().uri(uriBuilder -> uriBuilder.path("/custodias/{id}").build(idCustodiaEquipo))
					.retrieve().bodyToMono(CustodiasResponseDTO.class).block();
		} catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new RuntimeException("Custodia no encontrada con id: " + idCustodiaEquipo);
			}
			throw e;
		}
	}

	@Override
	public void actualizarCustodia(Integer idCustodiaEquipo, CustodiasRequestDTO dto) {
		clienteWeb.put().uri(uriBuilder -> uriBuilder.path("/custodias/{id}").build(idCustodiaEquipo)).bodyValue(dto)
				.retrieve().toBodilessEntity().block();
	}

	@Override
	public void actualizarEstado(Integer idCustodiaEquipo, boolean estado) {
		CustodiasRequestDTO dto = new CustodiasRequestDTO();
		dto.setEstado(estado);

		clienteWeb.put().uri("/custodias/estado/{id}", idCustodiaEquipo).bodyValue(dto).retrieve().toBodilessEntity()
				.block();
	}
}
