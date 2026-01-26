package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;

@Mapper(componentModel = "spring")
public interface IEquiposDtoMapper {

  
    @Mapping(target = "fkDepartamento", expression = "java(mapDepartamento(dto))")
    Equipos toDomain(EquiposRequestDTO dto);

    EquiposResponseDTO toResponseDto(Equipos equipo);

    default DepartamentosJpa mapDepartamento(EquiposRequestDTO dto) {
        if (dto == null || dto.getFkDepartamento() == null) return null;
        DepartamentosJpa d = new DepartamentosJpa();
        d.setIdDepartamento(dto.getFkDepartamento().getIdDepartamento());
        return d;
    }
}
