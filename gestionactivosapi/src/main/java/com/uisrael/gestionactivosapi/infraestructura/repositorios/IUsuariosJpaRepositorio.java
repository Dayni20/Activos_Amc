package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UsuariosJpa;

public interface IUsuariosJpaRepositorio extends JpaRepository<UsuariosJpa, Integer> {
	
	Optional<UsuariosJpa> findByCorreo(String correo);

}
