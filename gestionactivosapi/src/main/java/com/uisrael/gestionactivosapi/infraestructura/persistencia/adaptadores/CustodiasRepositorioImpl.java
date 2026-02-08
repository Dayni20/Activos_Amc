package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiasRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiasJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiasJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IEquiposJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiosJpaRepositorio;

public class CustodiasRepositorioImpl implements ICustodiasRepositorio {

	private final ICustodiasJpaRepositorio jpaRepository;
	private final ICustodiasJpaMapper entityMapper;

	private final IEquiposJpaRepositorio equiposRepo;
	private final ICustodiosJpaRepositorio custodiosRepo;

	// 👉 Constructor usado por @Bean en ConfiguracionGeneral
	public CustodiasRepositorioImpl(ICustodiasJpaRepositorio jpaRepository, ICustodiasJpaMapper entityMapper,
			IEquiposJpaRepositorio equiposRepo, ICustodiosJpaRepositorio custodiosRepo) {
		this.jpaRepository = jpaRepository;
		this.entityMapper = entityMapper;
		this.equiposRepo = equiposRepo;
		this.custodiosRepo = custodiosRepo;
	}

	// =========================
	// CREAR
	// =========================
	@Override
	public Custodias guardar(Custodias custodia) {

		CustodiasJpa entity = entityMapper.toEntity(custodia);

		if (custodia.getFkEquipo() != null) {
			entity.setFkEquipo(equiposRepo.getReferenceById(custodia.getFkEquipo().getIdEquipo()));
		}

		if (custodia.getFkCustodio() != null) {
			entity.setFkCustodio(custodiosRepo.getReferenceById(custodia.getFkCustodio().getIdCustodio()));
		}

		CustodiasJpa guardado = jpaRepository.save(entity);

		// 🔑 volver a leer para traer relaciones completas
		CustodiasJpa completo = jpaRepository.findById(guardado.getIdCustodiaEquipo())
				.orElseThrow(() -> new RuntimeException("No se pudo leer la custodia guardada"));

		return entityMapper.toDomain(completo);
	}

	// =========================
	// BUSCAR POR ID
	// =========================
	@Override
	public Optional<Custodias> buscarPorId(int id) {
		return jpaRepository.findById(id).map(entityMapper::toDomain);
	}

	// =========================
	// LISTAR
	// =========================
	@Override
	public List<Custodias> listarTodos() {
		return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
	}

	// =========================
	// ACTUALIZAR
	// =========================
	@Override
	public Custodias actualizar(int id, Custodias custodia) {

		CustodiasJpa existente = jpaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Custodia no encontrada"));

		existente.setFechaInicio(custodia.getFechaInicio());
		existente.setFechaFin(custodia.getFechaFin());
		existente.setObservacion(custodia.getObservacion());
		existente.setEstado(custodia.isEstado());

		if (custodia.getFkEquipo() != null) {
			existente.setFkEquipo(equiposRepo.getReferenceById(custodia.getFkEquipo().getIdEquipo()));
		}

		if (custodia.getFkCustodio() != null) {
			existente.setFkCustodio(custodiosRepo.getReferenceById(custodia.getFkCustodio().getIdCustodio()));
		}

		CustodiasJpa guardado = jpaRepository.save(existente);

		CustodiasJpa completo = jpaRepository.findById(guardado.getIdCustodiaEquipo())
				.orElseThrow(() -> new RuntimeException("No se pudo leer la custodia actualizada"));

		return entityMapper.toDomain(completo);
	}

	// =========================
	// ACTUALIZAR ESTADO
	// =========================
	@Override
	public Custodias actualizarEstado(int id, Custodias custodia) {

		CustodiasJpa existente = jpaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Custodia no encontrada"));

		existente.setEstado(custodia.isEstado());

		CustodiasJpa guardado = jpaRepository.save(existente);

		CustodiasJpa completo = jpaRepository.findById(guardado.getIdCustodiaEquipo())
				.orElseThrow(() -> new RuntimeException("No se pudo leer la custodia"));

		return entityMapper.toDomain(completo);
	}	
}
