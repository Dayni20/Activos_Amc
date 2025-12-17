package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;

public interface IMarcasRepositorio extends JpaRepository<MarcasJpa, Integer> {

}
