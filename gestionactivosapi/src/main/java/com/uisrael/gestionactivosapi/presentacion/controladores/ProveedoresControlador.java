package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IProveedoresUseCase;
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
}
