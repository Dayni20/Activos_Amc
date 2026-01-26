package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiosResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.ICustodiosDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/custodios")
public class CustodiosControlador {

    private final ICustodiosUseCase custodiosUseCase;
    private final ICustodiosDtoMapper mapper;

    public CustodiosControlador(ICustodiosUseCase custodiosUseCase, ICustodiosDtoMapper mapper) {
        this.custodiosUseCase = custodiosUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustodiosResponseDTO crear(@Valid @RequestBody CustodiosRequestDTO request) {
        return mapper.toResponseDto(custodiosUseCase.crear(mapper.toDomain(request)));
    }

    @GetMapping
    public List<CustodiosResponseDTO> listar() {
        return custodiosUseCase.listar().stream().map(mapper::toResponseDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustodiosResponseDTO> obtenerPorId(@PathVariable int id) {
        Custodios c = custodiosUseCase.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustodiosResponseDTO> actualizar(@PathVariable int id,
                                                           @Valid @RequestBody CustodiosRequestDTO request) {
        Custodios actualizado = custodiosUseCase.actualizar(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponseDto(actualizado));
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<CustodiosResponseDTO> actualizarEstado(@PathVariable int id,
                                                                 @Valid @RequestBody CustodiosRequestDTO request) {
        Custodios actualizado = custodiosUseCase.actualizarEstado(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponseDto(actualizado));
    }
}
