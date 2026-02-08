package com.uisrael.consumogestionactivosapi.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
		clienteweb.post().uri("/marcas").bodyValue(dto).retrieve().toBodilessEntity().block();
		
	}

	@Override
	 public MarcasResponseDTO obtenerMarca(Integer id) {
        try {
            return clienteweb.get()
                    .uri("/marcas/{id}", id)
                    .retrieve()
                    .bodyToMono(MarcasResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Si no existe, devolvemos null para que el controlador maneje el caso
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

	@Override
	public void actualizarMarca(Integer id, MarcasRequestDTO dto) {
        clienteweb.put()
                .uri("/marcas/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

	@Override
	 public void eliminarMarca(Integer id) {
        clienteweb.delete()
                .uri("/marcas/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
	

}
