package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.dominio.repositorios.IProveedoresRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IProveedoresJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IProveedoresJpaRepositorio;

public class ProveedoresRepositorioImpl implements IProveedoresRepositorio {
	
    private final IProveedoresJpaRepositorio jpaRepository;
	
	private final IProveedoresJpaMapper entityMapper;
	
	public ProveedoresRepositorioImpl(IProveedoresJpaRepositorio jpaRepository,
			IProveedoresJpaMapper entityMapper) {
		this.jpaRepository = jpaRepository;
		this.entityMapper = entityMapper;
	}
	
	@Override
	public Proveedores guardar(Proveedores proveedores) {
		ProveedoresJpa entity = entityMapper.toEntity(proveedores);
		ProveedoresJpa guardado = jpaRepository.save(entity);
		return entityMapper.toDomain(guardado);
	}

	@Override
	public Optional<Proveedores> buscarPorId(int id) {
		return jpaRepository.findById(id).map(entityMapper::toDomain);
	}

	@Override
	public List<Proveedores> listarTodos() {
		return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
	}


}
