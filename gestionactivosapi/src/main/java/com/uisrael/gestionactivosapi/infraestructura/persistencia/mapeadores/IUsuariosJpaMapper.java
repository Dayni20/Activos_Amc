package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UsuariosJpa;

@Mapper(componentModel = "spring")
public interface IUsuariosJpaMapper {
	
	@Mapping(source = "idUsuario", target = "id_usuario")
	@Mapping(source = "idRol", target = "id_rol")
	@Mapping(source = "idDepartamento", target = "id_departamento")
	Usuarios toDomain(UsuariosJpa entity);
	
	@Mapping(source = "id_usuario", target = "idUsuario")
	@Mapping(source = "id_rol", target = "idRol")
	@Mapping(source = "id_departamento", target = "idDepartamento")
	UsuariosJpa toEntity(Usuarios usuario);

}
