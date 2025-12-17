package com.uisrael.gestionactivosapi.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiasJpa;

public interface ICustodiasRepositorio extends JpaRepository<CustodiasJpa, Integer> {

}
