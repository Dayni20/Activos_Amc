package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiosResponseDTO;

@Mapper(componentModel = "spring")
public interface ICustodiosDtoMapper {

    Custodios toDomain(CustodiosRequestDTO dto);

    CustodiosResponseDTO toResponseDto(Custodios custodio);
}
