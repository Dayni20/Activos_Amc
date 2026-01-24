package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.ProveedoresRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.ProveedoresResponseDTO;

@Mapper(componentModel = "spring")
public interface IProveedoresDtoMapper {

	Proveedores toDomain(ProveedoresRequestDTO dto);

	ProveedoresResponseDTO toResponseDto(Proveedores proveedor);
}