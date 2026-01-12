package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;

@Mapper (componentModel = "spring")
public class IProveedoresJpaMapper {
public Proveedores toDomain(ProveedoresJpa entity) {
	return null;
}
	
	public ProveedoresJpa toEntity(Proveedores Proveedores) {
		return null;
	}
}
