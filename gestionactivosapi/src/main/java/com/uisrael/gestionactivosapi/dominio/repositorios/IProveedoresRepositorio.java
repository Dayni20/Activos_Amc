package com.uisrael.gestionactivosapi.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;


public interface IProveedoresRepositorio {
	
   Proveedores guardar( Proveedores  Proveedores);
	
	Optional < Proveedores> buscarPorId(int id);
	
	List<Proveedores> listarTodos();
	
	Proveedores actualizar(int id, Proveedores proveedores);

	void eliminar(int id);

}
