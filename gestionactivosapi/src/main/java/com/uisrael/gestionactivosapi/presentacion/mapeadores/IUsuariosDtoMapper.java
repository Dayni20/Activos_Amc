package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.RolesJpa;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.RolesRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.UsuariosRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.RolesResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.UsuariosResponseDTO;

@Mapper(componentModel = "spring")
public interface IUsuariosDtoMapper {
	
	Usuarios toDomain(UsuariosRequestDTO dto);
	
	UsuariosResponseDTO toResponseDto(Usuarios usuario);

	// helper mappings for nested role mapping
	RolesJpa map(RolesRequestDTO dto);
	RolesResponseDTO map(RolesJpa entity);

}
