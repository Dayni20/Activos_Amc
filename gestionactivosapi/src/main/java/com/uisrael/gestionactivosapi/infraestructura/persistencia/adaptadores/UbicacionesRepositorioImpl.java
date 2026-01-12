package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUbicacionesRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UbicacionesJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IUbicacionesJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IUbicacionesJpaRepositorio;

public class UbicacionesRepositorioImpl implements IUbicacionesRepositorio {
	
	private final IUbicacionesJpaRepositorio jpaRepository;
	
	private final IUbicacionesJpaMapper entityMapper;
	
	

	public UbicacionesRepositorioImpl(IUbicacionesJpaRepositorio jpaRepository, IUbicacionesJpaMapper entityMapper) {
		super();
		this.jpaRepository = jpaRepository;
		this.entityMapper = entityMapper;
	}

	@Override
	public Ubicaciones guardar(Ubicaciones ubicacion) {
		UbicacionesJpa entity = entityMapper.toEntity(ubicacion);
		UbicacionesJpa guardado = jpaRepository.save(entity);
		return entityMapper.toDomain(guardado);
	}

	@Override
	public Optional<Ubicaciones> buscarPorId(int id) {
		return jpaRepository.findById(id).map(entityMapper::toDomain);
	}

	@Override
	public List<Ubicaciones> listarTodos() {
		return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
	}
	
	

}
