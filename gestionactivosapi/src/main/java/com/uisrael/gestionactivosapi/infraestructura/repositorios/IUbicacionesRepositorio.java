package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UbicacionesJpa;

public interface IUbicacionesRepositorio
        extends JpaRepository<UbicacionesJpa, Integer> {

}
