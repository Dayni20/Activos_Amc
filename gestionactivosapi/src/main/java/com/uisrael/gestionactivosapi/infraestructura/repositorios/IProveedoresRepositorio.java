package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;

public interface IProveedoresRepositorio extends JpaRepository<ProveedoresJpa, Integer> {

}
