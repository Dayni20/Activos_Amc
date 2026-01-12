package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;

public interface IEquiposJpaRepositorio extends JpaRepository<EquiposJpa, Integer> {

}
