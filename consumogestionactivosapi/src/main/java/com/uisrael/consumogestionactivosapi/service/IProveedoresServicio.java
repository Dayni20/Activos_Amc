package com.uisrael.consumogestionactivosapi.service;

import java.util.List;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.ProveedoresRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.ProveedoresResponseDTO;


public interface IProveedoresServicio {

	//Listar
	public List<ProveedoresResponseDTO> listarProveedores();

	//Nuevo
	public void nuevoProveedores(ProveedoresRequestDTO dto);

	//Obtener
	 ProveedoresResponseDTO obtenerProveedor(Integer id);

	//Editar
	 void actualizarProveedor(Integer id, ProveedoresRequestDTO dto);

	//Eliminar
	 void eliminarProveedor(Integer id);

}
