package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.UbicacionesRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.UbicacionesResponseDTO;

@Mapper(componentModel = "spring")
public interface IUbicacionesDtoMapper {
	
	Ubicaciones toDomain(UbicacionesRequestDTO dto);
	
	UbicacionesResponseDTO toResponseDto(Ubicaciones ubicacion);

}
