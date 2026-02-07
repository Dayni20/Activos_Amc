package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Cargos;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CargosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CargosResponseDTO;

@Mapper(componentModel = "spring")
public interface ICargoDtoMapper {

	Cargos toDomain(CargosRequestDTO dto);

	CargosResponseDTO toResponseDto(Cargos cargo);
}
