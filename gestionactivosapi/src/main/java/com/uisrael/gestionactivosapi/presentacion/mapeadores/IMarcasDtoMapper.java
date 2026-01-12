package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.MarcasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.MarcasResponseDTO;


@Mapper(componentModel = "spring")
public interface IMarcasDtoMapper {

    default Marcas toDomain(MarcasRequestDTO dto) {
        return Marcas.of(
                dto.getId_marca(),
                dto.getNombre(),
                true
        );
    }

    MarcasResponseDTO toResponseDto(Marcas marca);
}
