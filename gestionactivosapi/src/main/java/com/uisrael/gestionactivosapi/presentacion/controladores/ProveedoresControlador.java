package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IProveedoresUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.ProveedoresRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.ProveedoresResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IProveedoresDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedoresControlador {

    private final IProveedoresUseCase proveedoresUseCase;
    private final IProveedoresDtoMapper mapper;

    public ProveedoresControlador(IProveedoresUseCase proveedoresUseCase, IProveedoresDtoMapper mapper) {
        this.proveedoresUseCase = proveedoresUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProveedoresResponseDTO crear(@Valid @RequestBody ProveedoresRequestDTO request) {
        return mapper.toResponseDto(proveedoresUseCase.crear(mapper.toDomain(request)));
    }

    @GetMapping
    public List<ProveedoresResponseDTO> listar() {
        return proveedoresUseCase.listar().stream().map(mapper::toResponseDto).toList();
    }

    @PutMapping("/{id}")
	public ResponseEntity<ProveedoresResponseDTO> actualizar(@PathVariable int id,
			@Valid @RequestBody ProveedoresRequestDTO request) {

		Proveedores actualizado = proveedoresUseCase.actualizar(id, mapper.toDomain(request));
		return ResponseEntity.ok(mapper.toResponseDto(actualizado));
	}

    @DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable int id) {
		proveedoresUseCase.eliminar(id);
    }

    @GetMapping("/{id}")
  	public ProveedoresResponseDTO obtenerPorId(@PathVariable int id) {
  		return mapper.toResponseDto(proveedoresUseCase.obtenerPorId(id));
  	}
}
