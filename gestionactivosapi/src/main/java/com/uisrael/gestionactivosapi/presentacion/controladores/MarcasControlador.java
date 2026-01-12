package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IMarcasUseCase;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.MarcasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.MarcasResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IMarcasDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/marcas")
public class MarcasControlador {

    private final IMarcasUseCase marcasUseCase;
    private final IMarcasDtoMapper mapper;

    public MarcasControlador(IMarcasUseCase marcasUseCase, IMarcasDtoMapper mapper) {
        this.marcasUseCase = marcasUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MarcasResponseDTO crear(@Valid @RequestBody MarcasRequestDTO request) {
        return mapper.toResponseDto(marcasUseCase.crear(mapper.toDomain(request)));
    }

    @GetMapping
    public List<MarcasResponseDTO> listar() {
        return marcasUseCase.listar().stream().map(mapper::toResponseDto).toList();
    }
}
