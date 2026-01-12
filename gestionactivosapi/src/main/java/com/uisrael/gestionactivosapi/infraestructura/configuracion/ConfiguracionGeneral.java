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

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IProveedoresUseCase;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IMarcasUseCase;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.ProveedoresUseCaseImpl;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.MarcasUseCaseImpl;

import com.uisrael.gestionactivosapi.dominio.repositorios.IProveedoresRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IMarcasRepositorio;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.ProveedoresRepositorioImpl;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.MarcasRepositorioImpl;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IProveedoresJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IMarcasJpaMapper;

import com.uisrael.gestionactivosapi.infraestructura.repositorios.IProveedoresJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IMarcasJpaRepositorio;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IEquiposUseCase;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiosUseCase;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiasUseCase;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.EquiposUseCaseImpl;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.CustodiosUseCaseImpl;
import com.uisrael.gestionactivosapi.aplicacion.casosuso.impl.CustodiasUseCaseImpl;

import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiosRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiasRepositorio;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.EquiposRepositorioImpl;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.CustodiosRepositorioImpl;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores.CustodiasRepositorioImpl;

import com.uisrael.gestionactivosapi.infraestructura.repositorios.IEquiposJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiosJpaRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiasJpaRepositorio;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IEquiposJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiosJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiasJpaMapper;
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
	
	@Bean
	IProveedoresUseCase proveedoresUseCase(IProveedoresRepositorio repositorio) {
	    return new ProveedoresUseCaseImpl(repositorio);
	}

	@Bean
	IMarcasUseCase marcasUseCase(IMarcasRepositorio repositorio) {
	    return new MarcasUseCaseImpl(repositorio);
	}

	@Bean
	IProveedoresRepositorio proveedoresRepositorio(IProveedoresJpaRepositorio jpaRepositorio, IProveedoresJpaMapper mapper) {
	    return new ProveedoresRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IMarcasRepositorio marcasRepositorio(IMarcasJpaRepositorio jpaRepositorio, IMarcasJpaMapper mapper) {
	    return new MarcasRepositorioImpl(jpaRepositorio, mapper);
	}
    @Bean
    IEquiposUseCase equiposUseCase(IEquiposRepositorio repositorio) {
        return new EquiposUseCaseImpl(repositorio);
    }

    @Bean
    IEquiposRepositorio equiposRepositorio(
            IEquiposJpaRepositorio jpaRepositorio,
            IEquiposJpaMapper mapper) {
        return new EquiposRepositorioImpl(jpaRepositorio, mapper);
    }
    
    @Bean
    ICustodiosUseCase custodiosUseCase(ICustodiosRepositorio repositorio) {
        return new CustodiosUseCaseImpl(repositorio);
    }

    @Bean
    ICustodiosRepositorio custodiosRepositorio(
            ICustodiosJpaRepositorio jpaRepositorio,
            ICustodiosJpaMapper mapper) {
        return new CustodiosRepositorioImpl(jpaRepositorio, mapper);
    }

    @Bean
    ICustodiasUseCase custodiasUseCase(ICustodiasRepositorio repositorio) {
        return new CustodiasUseCaseImpl(repositorio);
    }

    @Bean
    ICustodiasRepositorio custodiasRepositorio(
            ICustodiasJpaRepositorio jpaRepositorio,
            ICustodiasJpaMapper mapper) {
        return new CustodiasRepositorioImpl(jpaRepositorio, mapper);
    }
}
