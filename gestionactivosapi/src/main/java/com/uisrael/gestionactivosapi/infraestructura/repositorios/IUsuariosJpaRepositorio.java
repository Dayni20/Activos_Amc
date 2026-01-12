package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UsuariosJpa;

public interface IUsuariosJpaRepositorio extends JpaRepository<UsuariosJpa, Integer> {

}
