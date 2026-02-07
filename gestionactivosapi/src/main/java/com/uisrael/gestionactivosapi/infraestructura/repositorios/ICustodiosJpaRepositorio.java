package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;

public interface ICustodiosJpaRepositorio extends JpaRepository<CustodiosJpa, Integer> {
	
	boolean existsByCedulaIgnoreCase(String cedula);

	boolean existsByCedulaIgnoreCaseAndIdCustoodioNot(String cedula, Integer idCustodio);

	boolean existsByCorreoIgnoreCase(String correo);

	boolean existsByCorreoIgnoreCaseAndIdCustoodioNot(String correo, Integer idCustodio);

}
