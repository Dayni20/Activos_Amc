package com.uisrael.gestionactivosapi.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IDepartamentosUseCase;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUbicacionesUseCase;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.DepartamentosUseCaseImpl;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.UbicacionesUseCaseImpl;
import com.uisrael.gestionactivosapi.dominio.repositorios.IDepartamentosRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUbicacionesRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.DepartamentosRepositorioImpl;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.UbicacionesRepositorioImpl;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IDepartamentosJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IUbicacionesJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IDepartamentosJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IUbicacionesJpaRepositorio;

@Configuration
public class ConfiguracionGeneral {
	
	@Bean
	IDepartamentosUseCase departamentoUseCase(IDepartamentosRepositorio repositorio) {
		return new DepartamentosUseCaseImpl(repositorio);
	}
	
	@Bean
	IUbicacionesUseCase ubicacionUseCase(IUbicacionesRepositorio repositorio) {
		return new UbicacionesUseCaseImpl(repositorio);
	}
	
	@Bean
	IDepartamentosRepositorio departamentoRepositorio(IDepartamentosJpaRepositorio jpaRepositorio, IDepartamentosJpaMapper mapper) {
		return new DepartamentosRepositorioImpl(jpaRepositorio, mapper);
	}
	
	@Bean
	IUbicacionesRepositorio ubicacionRepositorio(IUbicacionesJpaRepositorio jpaRepositorio, IUbicacionesJpaMapper mapper) {
		return new UbicacionesRepositorioImpl(jpaRepositorio, mapper);
	}
}
