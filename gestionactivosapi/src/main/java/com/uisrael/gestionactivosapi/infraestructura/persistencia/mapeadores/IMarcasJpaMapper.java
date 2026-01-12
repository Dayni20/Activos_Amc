package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;



@Mapper (componentModel = "spring")
public class IMarcasJpaMapper {
	public Marcas toDomain(MarcasJpa entity) {
		return null;
	}
		
		public MarcasJpa toEntity(Marcas Marcas) {
			return null;
		}
	}