package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CategoriaEquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CategoriaEquiposResponseDTO;

@Mapper(componentModel = "spring")
public interface ICategoriaEquiposDtoMapper {

	CategoriaEquipos toDomain(CategoriaEquiposRequestDTO dto);

	CategoriaEquiposResponseDTO toResponseDto(CategoriaEquipos categoriaEquipo);

}
