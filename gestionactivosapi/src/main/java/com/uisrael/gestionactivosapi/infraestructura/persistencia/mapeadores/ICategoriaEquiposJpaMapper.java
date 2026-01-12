package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CategoriaEquiposJpa;

@Mapper(componentModel = "spring")
public interface ICategoriaEquiposJpaMapper {
	
	@Mapping(source = "idCategoria", target = "id_categoria")
	CategoriaEquipos toDomain(CategoriaEquiposJpa entity);
	
	@Mapping(source = "id_categoria", target = "idCategoria")
	CategoriaEquiposJpa toEntity(CategoriaEquipos categoriaEquipo);

}
