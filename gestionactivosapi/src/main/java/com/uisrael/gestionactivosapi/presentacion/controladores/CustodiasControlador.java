package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiasUseCase;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiasResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.ICustodiasDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/custodias")
public class CustodiasControlador {

    private final ICustodiasUseCase custodiasUseCase;
    private final ICustodiasDtoMapper mapper;

    public CustodiasControlador(ICustodiasUseCase custodiasUseCase,
                                ICustodiasDtoMapper mapper) {
        this.custodiasUseCase = custodiasUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustodiasResponseDTO crear(@Valid @RequestBody CustodiasRequestDTO request) {
        return mapper.toResponseDto(
                custodiasUseCase.crear(mapper.toDomain(request))
        );
    }

    @GetMapping
    public List<CustodiasResponseDTO> listar() {
        return custodiasUseCase.listar()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
