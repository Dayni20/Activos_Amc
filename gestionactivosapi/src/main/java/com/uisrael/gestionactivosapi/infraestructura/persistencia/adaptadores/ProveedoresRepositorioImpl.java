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

	@Override
	public Proveedores actualizar(int id, Proveedores proveedores) {
		ProveedoresJpa existente = jpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

		existente.setNombre(proveedores.getNombre());
		existente.setRuc(proveedores.getRuc());
		existente.setTelefono(proveedores.getTelefono());
		existente.setCorreo(proveedores.getCorreo());
		existente.setDireccion(proveedores.getDireccion());
		existente.setEstado(proveedores.isEstado());


		ProveedoresJpa guardado = jpaRepository.save(existente);
		return entityMapper.toDomain(guardado);
	}

		@Override
		public void eliminar(int id) {
			ProveedoresJpa entity = jpaRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Poveedor no encontrado"));
			entity.setEstado(false);
			jpaRepository.save(entity);
		}

}
