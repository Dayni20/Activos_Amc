package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.RolesJpa;

public interface IRolesRepositorio
        extends JpaRepository<RolesJpa, Integer> {

}
