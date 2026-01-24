package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;

@Mapper(componentModel = "spring")
public interface IProveedoresJpaMapper {

	Proveedores toDomain(ProveedoresJpa entity);

	ProveedoresJpa toEntity(Proveedores proveedor);
}
