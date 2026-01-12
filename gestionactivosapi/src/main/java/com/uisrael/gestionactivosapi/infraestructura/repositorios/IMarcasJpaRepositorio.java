package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;

public interface IMarcasJpaRepositorio extends JpaRepository<MarcasJpa, Integer> {

	List<Marcas> listarTodos();

	Marcas guardar(Marcas marcas);

	Optional<Marcas> buscarPorId(int id);

}
