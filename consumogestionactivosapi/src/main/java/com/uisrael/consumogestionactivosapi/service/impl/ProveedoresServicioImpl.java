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

	    // 1) Normaliza el RUC (trim y dejar solo dígitos por si ponen guiones/espacios)
	    String rucNuevo = normalizarRuc(dto.getRuc());  // 👈 asegúrate que tu DTO tenga getRuc()

	    // Validación básica
	    if (rucNuevo.length() != 13) {
	        throw new IllegalStateException("El RUC debe tener 13 dígitos");
	    }

	    // 2) Consulto existentes
	    List<ProveedoresResponseDTO> existentes = listarProveedores();

	    boolean existePorRuc = existentes.stream()
	            .anyMatch(p -> normalizarRuc(p.getRuc()).equals(rucNuevo)); // 👈 tu ResponseDTO debe tener getRuc()

	    if (existePorRuc) {
	        throw new IllegalStateException("El proveedor ya existe (RUC duplicado)");
	    }

	    // (Opcional) también validar por nombre ignorando mayúsculas/minúsculas
	    String nombreNuevo = normalizarTexto(dto.getNombre());
	    boolean existePorNombre = existentes.stream()
	            .anyMatch(p -> normalizarTexto(p.getNombre()).equals(nombreNuevo));

	    if (existePorNombre) {
	        throw new IllegalStateException("El proveedor ya existe (Nombre duplicado)");
	    }

	    // 3) Guardar
	    clienteweb.post()
	            .uri("/proveedores")
	            .bodyValue(dto)
	            .retrieve()
	            .toBodilessEntity()
	            .block();
	}

	private String normalizarRuc(String ruc) {
	    if (ruc == null) {
			return "";
		}
	    // deja solo números
	    return ruc.trim().replaceAll("\\D", "");
	}

	private String normalizarTexto(String texto) {
	    return texto == null ? "" : texto.trim().toUpperCase();
	}

	@Override
	 public ProveedoresResponseDTO obtenerProveedor(Integer id) {
        return clienteweb.get()
                .uri("/proveedores/{id}", id)
                .retrieve()
                .bodyToMono(ProveedoresResponseDTO.class)
                .block();
    }

	@Override
	public void actualizarProveedor(Integer id, ProveedoresRequestDTO dto) {

	    int idEditando = id; // convierte Integer a int

	    String rucNuevo = normalizarRuc(dto.getRuc());
	    String nombreNuevo = normalizarTexto(dto.getNombre());

	    List<ProveedoresResponseDTO> existentes = listarProveedores();

	    boolean rucDuplicado = existentes.stream()
	            .filter(p -> p.getIdProveedor() != idEditando) // ✅ excluye el mismo registro
	            .anyMatch(p -> normalizarRuc(p.getRuc()).equals(rucNuevo));

	    if (rucDuplicado) {
	        throw new IllegalStateException("Ya existe un proveedor con ese RUC");
	    }

	    boolean nombreDuplicado = existentes.stream()
	            .filter(p -> p.getIdProveedor() != idEditando) // ✅ excluye el mismo registro
	            .anyMatch(p -> normalizarTexto(p.getNombre()).equals(nombreNuevo));

	    if (nombreDuplicado) {
	        throw new IllegalStateException("Ya existe un proveedor con ese nombre");
	    }

	    clienteweb.put()
	            .uri("/proveedores/{id}", id)
	            .bodyValue(dto)
	            .retrieve()
	            .toBodilessEntity()
	            .block();
	}

	@Override
	public void eliminarProveedor(Integer id) {
        // Si tu API usa DELETE:
        clienteweb.delete()
                .uri("/proveedores/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
