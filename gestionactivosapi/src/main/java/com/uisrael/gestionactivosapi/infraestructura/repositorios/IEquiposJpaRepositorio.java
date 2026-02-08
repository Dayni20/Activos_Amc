package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;

public interface IEquiposJpaRepositorio extends JpaRepository<EquiposJpa, Integer> {
	
	boolean existsByCodigoSapIgnoreCase(String codigo);

	boolean existsByCodigoSapIgnoreCaseAndIdEquipoNot(String codigo, Integer idEquipo);

	boolean existsBySerialIgnoreCase(String serial);

	boolean existsBySerialIgnoreCaseAndIdEquipoNot(String serial, Integer idEquipo);

	boolean existsByIpIgnoreCase(String ip);

	boolean existsByIpIgnoreCaseAndIdEquipoNot(String ip, Integer idEquipo);
	
	boolean existsByMacIgnoreCase(String mac);

	boolean existsByMacIgnoreCaseAndIdEquipoNot(String mac, Integer idEquipo);

}
