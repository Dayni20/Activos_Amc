package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;

@Mapper(componentModel = "spring")
public interface ICustodiosJpaMapper {

    Custodios toDomain(CustodiosJpa entity);

    CustodiosJpa toEntity(Custodios custodio);
}
