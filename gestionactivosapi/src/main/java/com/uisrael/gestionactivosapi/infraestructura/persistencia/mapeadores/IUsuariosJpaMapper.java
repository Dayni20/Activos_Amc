package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UsuariosJpa;

@Mapper(componentModel = "spring")
public interface IUsuariosJpaMapper {
	
	@Mapping(source = "idUsuario", target = "idUsuario")
	Usuarios toDomain(UsuariosJpa entity);
	
	@Mapping(source = "idUsuario", target = "idUsuario")
	UsuariosJpa toEntity(Usuarios usuario);

}
