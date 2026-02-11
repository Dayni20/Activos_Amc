package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CategoriaEquiposJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IEquiposJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IEquiposJpaRepositorio;

public class EquiposRepositorioImpl implements IEquiposRepositorio {

    private final IEquiposJpaRepositorio jpaRepository;
    private final IEquiposJpaMapper entityMapper;

    public EquiposRepositorioImpl(IEquiposJpaRepositorio jpaRepository,
                                  IEquiposJpaMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Equipos guardar(Equipos equipo) {
        EquiposJpa entity = entityMapper.toEntity(equipo);

        // ✅ FK Marca (en JPA se llama fkMarcas)
        if (equipo.getFkMarca() != null) {
            MarcasJpa marca = new MarcasJpa();
            marca.setIdMarca(equipo.getFkMarca().getIdMarca());
            entity.setFkMarcas(marca);
        }

        // ✅ FK Proveedor (en JPA se llama proveedor)
        if (equipo.getFkProveedor() != null) {
            ProveedoresJpa prov = new ProveedoresJpa();
            prov.setIdProveedor(equipo.getFkProveedor().getIdProveedor());
            entity.setFkProveedor(prov);
        }

        EquiposJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Equipos> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Equipos> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public Equipos actualizar(int id, Equipos equipo) {
        EquiposJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        existente.setCodigoSap(equipo.getCodigoSap());
        existente.setTipoEquipo(equipo.getTipoEquipo());
        existente.setModelo(equipo.getModelo());
        existente.setSerial(equipo.getSerial());
        existente.setProcesador(equipo.getProcesador());
        existente.setMemoriaRamGb(equipo.getMemoriaRamGb());
        existente.setCapacidadAlmacenamientoGb(equipo.getCapacidadAlmacenamientoGb());
        existente.setSistemaOperativo(equipo.getSistemaOperativo());
        existente.setLicenciaWindowsActivada(equipo.getLicenciaWindowsActivada());
        existente.setEtiquetaActivoFijo(equipo.getEtiquetaActivoFijo());
        existente.setTipoLicenciaOffice(equipo.getTipoLicenciaOffice());
        existente.setVersionOffice(equipo.getVersionOffice());
        existente.setUnionDominio(equipo.getUnionDominio());
        existente.setIp(equipo.getIp());
        existente.setMac(equipo.getMac());
        existente.setFechaCompra(equipo.getFechaCompra());
        existente.setPrecioCompra(equipo.getPrecioCompra());
        existente.setEstadoEquipo(equipo.getEstadoEquipo());
        existente.setObservacionEquipo(equipo.getObservacionEquipo());
        existente.setEstado(equipo.isEstado());

        // ✅ FK Marca (fkMarcas)
        if (equipo.getFkMarca() != null) {
            MarcasJpa marca = new MarcasJpa();
            marca.setIdMarca(equipo.getFkMarca().getIdMarca());
            existente.setFkMarcas(marca);
        }

        // ✅ FK Proveedor (proveedor)
        if (equipo.getFkProveedor() != null) {
            ProveedoresJpa prov = new ProveedoresJpa();
            prov.setIdProveedor(equipo.getFkProveedor().getIdProveedor());
            existente.setFkProveedor(prov);
        }

        // ✅ FK Categoria (categoria)
        if (equipo.getFkCategoria() != null) {
            CategoriaEquiposJpa cat = new CategoriaEquiposJpa();
            cat.setIdCategoria(equipo.getFkCategoria().getIdCategoria());
            existente.setFkCategoria(cat);
        }

        EquiposJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Equipos actualizarEstado(int id, boolean estado) {
        EquiposJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        existente.setEstado(estado);

        EquiposJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

	@Override
	public boolean existeCodigo(String codigo) {
		return jpaRepository.existsByCodigoSapIgnoreCase(codigo);
	}

	@Override
	public boolean existeCodigoParaOtro(String codigo, int idEquipo) {
		return jpaRepository.existsByCodigoSapIgnoreCaseAndIdEquipoNot(codigo, idEquipo);
	}

	@Override
	public boolean existeSerial(String serial) {
		return jpaRepository.existsBySerialIgnoreCase(serial);
	}

	@Override
	public boolean existeSerialParaOtro(String serial, int idEquipo) {
		return jpaRepository.existsBySerialIgnoreCaseAndIdEquipoNot(serial, idEquipo);
	}

	@Override
	public boolean existeIP(String ip) {
		return jpaRepository.existsByIpIgnoreCase(ip);
	}

	@Override
	public boolean existeIPParaOtro(String ip, int idEquipo) {
		return jpaRepository.existsByIpIgnoreCaseAndIdEquipoNot(ip, idEquipo);
	}

	@Override
	public boolean existeMAC(String mac) {
		return jpaRepository.existsByMacIgnoreCase(mac);
	}

	@Override
	public boolean existeMACParaOtro(String mac, int idEquipo) {
		return jpaRepository.existsByMacIgnoreCaseAndIdEquipoNot(mac, idEquipo);
	}

}
