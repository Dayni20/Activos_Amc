package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Departamentos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IDepartamentosRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IDepartamentosJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IDepartamentosJpaRepositorio;

public class DepartamentosRepositorioImpl implements IDepartamentosRepositorio{

	private final IDepartamentosJpaRepositorio jpaRepository;
	
	private final IDepartamentosJpaMapper entityMapper;

	public DepartamentosRepositorioImpl(IDepartamentosJpaRepositorio jpaRepository,
			IDepartamentosJpaMapper entityMapper) {
		this.jpaRepository = jpaRepository;
		this.entityMapper = entityMapper;
	}

	@Override
	public Departamentos guardar(Departamentos departamento) {
		DepartamentosJpa entity = entityMapper.toEntity(departamento);
		DepartamentosJpa guardado = jpaRepository.save(entity);
		return entityMapper.toDomain(guardado);
	}

	@Override
	public Optional<Departamentos> buscarPorId(int id) {
		return jpaRepository.findById(id).map(entityMapper::toDomain);
	}

	@Override
	public List<Departamentos> listarTodos() {
		return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
	}

	
}
