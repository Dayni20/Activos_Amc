package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiasResponseDTO;

@Mapper(componentModel = "spring")
public interface ICustodiasDtoMapper {

    Custodias toDomain(CustodiasRequestDTO dto);

    CustodiasResponseDTO toResponseDto(Custodias custodia);
}
