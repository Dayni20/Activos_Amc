package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiosRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CargosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiosJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiosJpaRepositorio;

public class CustodiosRepositorioImpl implements ICustodiosRepositorio {

    private final ICustodiosJpaRepositorio jpaRepository;
    private final ICustodiosJpaMapper entityMapper;

    public CustodiosRepositorioImpl(ICustodiosJpaRepositorio jpaRepository, ICustodiosJpaMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Custodios guardar(Custodios custodio) {
        CustodiosJpa entity = entityMapper.toEntity(custodio);
        CustodiosJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Custodios> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Custodios> listarTodos() {
        return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
    }

    @Override
    public Custodios actualizar(int id, Custodios custodio) {
        CustodiosJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custodio no encontrado"));

        existente.setNombre(custodio.getNombre());
        existente.setCedula(custodio.getCedula());
        existente.setCorreo(custodio.getCorreo());
        existente.setTelefono(custodio.getTelefono());
        existente.setEstado(custodio.isEstado());
        existente.setFechaIngreso(custodio.getFechaIngreso());
        
		// Actualizar departamento por id si viene
		if (custodio.getFkDepartamento() != null) {
			DepartamentosJpa dep = new DepartamentosJpa();
			dep.setIdDepartamento(custodio.getFkDepartamento().getIdDepartamento());
			existente.setFkDepartamento(dep);
		}
		
		if (custodio.getFkCargo() != null) {
			CargosJpa car = new CargosJpa();
			car.setIdCargo(custodio.getFkCargo().getIdCargo());
			existente.setFkCargo(car);
		}

        CustodiosJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Custodios actualizarEstado(int id, Custodios custodio) {
        CustodiosJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custodio no encontrado"));

        existente.setEstado(custodio.isEstado());

        CustodiosJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

	@Override
	public boolean existeCorreo(String correo) {
		return jpaRepository.existsByCorreoIgnoreCase(correo);
	}

	@Override
	public boolean existeCorreoParaOtro(String correo, int idCustodio) {
		return jpaRepository.existsByCorreoIgnoreCaseAndIdCustodioNot(correo, idCustodio);
	}

	@Override
	public boolean existeCedula(String cedula) {
		return jpaRepository.existsByCedulaIgnoreCase(cedula);
	}

	@Override
	public boolean existeCedulaParaOtro(String cedula, int idCustodio) {
		return jpaRepository.existsByCedulaIgnoreCaseAndIdCustodioNot(cedula, idCustodio);
		
	}
}
