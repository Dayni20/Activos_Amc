package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;


public interface IProveedoresUseCase {
Proveedores crear (Proveedores Proveedores);
	
Proveedores obtenerPorId(int id);
	
	List<Proveedores> listar();
}
