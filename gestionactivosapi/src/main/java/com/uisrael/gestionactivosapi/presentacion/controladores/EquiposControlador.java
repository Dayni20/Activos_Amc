package com.uisrael.gestionactivosapi.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IEquiposUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.mapeadores.IEquiposDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquiposControlador {

    private final IEquiposUseCase equiposUseCase;
    private final IEquiposDtoMapper mapper;

    public EquiposControlador(IEquiposUseCase equiposUseCase, IEquiposDtoMapper mapper) {
        this.equiposUseCase = equiposUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquiposResponseDTO crear(@Valid @RequestBody EquiposRequestDTO request) {
        return mapper.toResponseDto(equiposUseCase.crear(mapper.toDomain(request)));
    }

    @GetMapping
    public List<EquiposResponseDTO> listar() {
        return equiposUseCase.listar().stream().map(mapper::toResponseDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquiposResponseDTO> obtenerPorId(@PathVariable int id) {
        Equipos equipo = equiposUseCase.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(equipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquiposResponseDTO> actualizar(@PathVariable int id,
            @Valid @RequestBody EquiposRequestDTO request) {
        Equipos actualizado = equiposUseCase.actualizar(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponseDto(actualizado));
    }

	    @PutMapping("/estado/{id}")
	    public ResponseEntity<EquiposResponseDTO> actualizarEstado(@PathVariable int id,
	            @Valid @RequestBody EquiposRequestDTO request) {
	        Equipos actualizadoEstado = equiposUseCase.actualizarEstado(id, mapper.toDomain(request));
	        return ResponseEntity.ok(mapper.toResponseDto(actualizadoEstado));
	    }
}
