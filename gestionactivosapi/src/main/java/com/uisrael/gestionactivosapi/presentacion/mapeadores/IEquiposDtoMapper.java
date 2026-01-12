package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;

@Mapper(componentModel = "spring")
public interface IEquiposDtoMapper {

    Equipos toDomain(EquiposRequestDTO dto);

    EquiposResponseDTO toResponseDto(Equipos equipo);
}
