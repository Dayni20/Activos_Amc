package com.uisrael.consumogestionactivosapi.service;

import java.util.List;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.CustodiasRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.CustodiasResponseDTO;

public interface ICustodiasServicio {

    List<CustodiasResponseDTO> listarCustodias();

    void crearCustodia(CustodiasRequestDTO dto);

    CustodiasResponseDTO obtenerPorId(Integer idCustodiaEquipo);

    void actualizarCustodia(Integer idCustodiaEquipo, CustodiasRequestDTO dto);

    void actualizarEstado(Integer idCustodiaEquipo, boolean estado);
}
