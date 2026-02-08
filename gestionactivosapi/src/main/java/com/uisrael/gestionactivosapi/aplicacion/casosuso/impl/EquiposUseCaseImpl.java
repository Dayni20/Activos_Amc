package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IEquiposUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;

public class EquiposUseCaseImpl implements IEquiposUseCase {

	private final IEquiposRepositorio repositorio;

	public EquiposUseCaseImpl(IEquiposRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Equipos crear(Equipos equipo) {
		if (repositorio.existeCodigo(equipo.getCodigoSap().trim())) {
			throw new RuntimeException("Ya existe un equipo con ese Codigo SAP");
		}

		if (repositorio.existeSerial(equipo.getSerial().trim())) {
			throw new RuntimeException("Ya existe un equipo con ese Serial");
		}

		if (equipo.getIp() != null && !equipo.getIp().isBlank()) {
		    String ip = equipo.getIp().trim();

		    if (repositorio.existeIP(ip)) {
		        throw new RuntimeException("Ya existe un equipo con esa dirección IP");
		    }

		}

		if (equipo.getMac() != null && !equipo.getMac().isBlank()) {
		    String mac = equipo.getMac().trim().toUpperCase();

		    if (repositorio.existeMAC(mac)) {
		        throw new RuntimeException("Ya existe un equipo con esa dirección MAC");
		    }

		}

		return repositorio.guardar(equipo);
	}

	@Override
	public Equipos obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
	}

	@Override
	public List<Equipos> listar() {
		return repositorio.listarTodos();
	}

	@Override
    public Equipos actualizar(int id, Equipos equipo) {
    	
    	if (repositorio.existeCodigoParaOtro(equipo.getCodigoSap().trim(), id)) {
			throw new RuntimeException("Ya existe un equipo con ese Codigo SAP");
		}
    	
    	if (repositorio.existeSerialParaOtro(equipo.getSerial().trim(), id)) {
			throw new RuntimeException("Ya existe un equipo con ese Serial");
		}
    	
    	if (repositorio.existeIPParaOtro(equipo.getSerial().trim(), id)) {
			throw new RuntimeException("Ya existe un equipo con esa dirección IP");
		}
    	
    	if (repositorio.existeMACParaOtro(equipo.getSerial().trim(), id)) {
			throw new RuntimeException("Ya existe un equipo con esa dirección MAC");
		}
    	
    	Equipos actualizado = new Equipos(
    			id,
    			equipo.getCodigoSap(),
    	        equipo.getTipoEquipo(),
    	        equipo.getModelo(),
    	        equipo.getSerial(),
    	        equipo.getProcesador(),
    	        equipo.getMemoriaRamGb(),
    	        equipo.getCapacidadAlmacenamientoGb(),
    	        equipo.getSistemaOperativo(),
    	        equipo.getLicenciaWindowsActivada(),
    	        equipo.getEtiquetaActivoFijo(),
    	        equipo.getTipoLicenciaOffice(),
    	        equipo.getVersionOffice(),
    	        equipo.getUnionDominio(),
    	        equipo.getIp(),
    	        equipo.getMac(),
    	        equipo.getFechaCompra(),
    	        equipo.getPrecioCompra(),
    	        equipo.getEstadoEquipo(),
    	        equipo.getObservacionEquipo(),
    	        equipo.isEstado(),
    	        equipo.getFkMarca(),
    	        equipo.getFkCategoria(),
    	        equipo.getFkProveedor()
    			);
    	
        return repositorio.actualizar(id, actualizado);
    }

	@Override
	public Equipos actualizarEstado(int id, boolean estado) {
		
		Equipos equipo = repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
		
		Equipos actualizado = new Equipos(
    			id,
    			equipo.getCodigoSap(),
    	        equipo.getTipoEquipo(),
    	        equipo.getModelo(),
    	        equipo.getSerial(),
    	        equipo.getProcesador(),
    	        equipo.getMemoriaRamGb(),
    	        equipo.getCapacidadAlmacenamientoGb(),
    	        equipo.getSistemaOperativo(),
    	        equipo.getLicenciaWindowsActivada(),
    	        equipo.getEtiquetaActivoFijo(),
    	        equipo.getTipoLicenciaOffice(),
    	        equipo.getVersionOffice(),
    	        equipo.getUnionDominio(),
    	        equipo.getIp(),
    	        equipo.getMac(),
    	        equipo.getFechaCompra(),
    	        equipo.getPrecioCompra(),
    	        equipo.getEstadoEquipo(),
    	        equipo.getObservacionEquipo(),
    	        estado,
    	        equipo.getFkMarca(),
    	        equipo.getFkCategoria(),
    	        equipo.getFkProveedor()
    			);
    	
        return repositorio.actualizar(id, actualizado);
	}

	@Override
	public boolean existeCodigo(String codigo) {
		return repositorio.existeCodigo(codigo.trim());
	}

	@Override
	public boolean existeCodigoParaOtro(String codigo, int idEquipo) {
		return repositorio.existeCodigoParaOtro(codigo.trim(), idEquipo);
	}

	@Override
	public boolean existeSerial(String serial) {
		return repositorio.existeSerial(serial.trim());
	}

	@Override
	public boolean existeSerialParaOtro(String serial, int idEquipo) {
		return repositorio.existeCodigoParaOtro(serial.trim(), idEquipo);
	}

	@Override
	public boolean existeIP(String ip) {
		return repositorio.existeIP(ip.trim());
	}

	@Override
	public boolean existeIPParaOtro(String ip, int idEquipo) {
		return repositorio.existeIPParaOtro(ip.trim(), idEquipo);
	}

	@Override
	public boolean existeMAC(String mac) {
		return repositorio.existeMAC(mac.trim());
	}

	@Override
	public boolean existeMACParaOtro(String mac, int idEquipo) {
		return repositorio.existeMACParaOtro(mac.trim(), idEquipo);
	}

}
