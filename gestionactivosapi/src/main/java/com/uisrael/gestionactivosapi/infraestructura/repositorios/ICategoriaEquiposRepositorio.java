package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CategoriaEquiposJpa;

public interface ICategoriaEquiposRepositorio 
        extends JpaRepository<CategoriaEquiposJpa, Integer> {

}