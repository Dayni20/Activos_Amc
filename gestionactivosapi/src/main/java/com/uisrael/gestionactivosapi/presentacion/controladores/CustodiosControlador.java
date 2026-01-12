package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiosUseCase;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiosResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.ICustodiosDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/custodios")
public class CustodiosControlador {

    private final ICustodiosUseCase custodiosUseCase;
    private final ICustodiosDtoMapper mapper;

    public CustodiosControlador(ICustodiosUseCase custodiosUseCase,
                                ICustodiosDtoMapper mapper) {
        this.custodiosUseCase = custodiosUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustodiosResponseDTO crear(@Valid @RequestBody CustodiosRequestDTO request) {
        return mapper.toResponseDto(
                custodiosUseCase.crear(mapper.toDomain(request))
        );
    }

    @GetMapping
    public List<CustodiosResponseDTO> listar() {
        return custodiosUseCase.listar()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
