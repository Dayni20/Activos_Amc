package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IEquiposUseCase;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IEquiposDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquiposControlador {

    private final IEquiposUseCase equiposUseCase;
    private final IEquiposDtoMapper mapper;

    public EquiposControlador(IEquiposUseCase equiposUseCase,
                              IEquiposDtoMapper mapper) {
        this.equiposUseCase = equiposUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquiposResponseDTO crear(@Valid @RequestBody EquiposRequestDTO request) {
        return mapper.toResponseDto(
                equiposUseCase.crear(mapper.toDomain(request))
        );
    }

    @GetMapping
    public List<EquiposResponseDTO> listar() {
        return equiposUseCase.listar()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
