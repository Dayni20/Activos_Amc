package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;

@Mapper(componentModel = "spring")
public interface IEquiposJpaMapper {

    Equipos toDomain(EquiposJpa entity);

    EquiposJpa toEntity(Equipos equipo);
}
