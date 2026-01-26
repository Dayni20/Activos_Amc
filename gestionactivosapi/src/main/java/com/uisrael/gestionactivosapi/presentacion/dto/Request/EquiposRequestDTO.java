package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class EquiposRequestDTO {

    private int idEquipo;
    private String codigoSap;
    private String tipoEquipo;
    private String modelo;
    private String serial;
    private String procesador;
    private Integer memoriaRamGb;
    private Integer capacidadAlmacenamientoGb;
    private String sistemaOperativo;
    private Boolean licenciaWindowsActivada;
    private Boolean etiquetaActivoFijo;
    private String tipoLicenciaOffice;
    private String versionOffice;
    private Boolean unionDominio;
    private String ip;
    private String mac;
    private LocalDate fechaCompra;
    private BigDecimal precioCompra;
    private String estadoEquipo;
    private String observacionEquipo;
    private boolean estado;

    private DepartamentosRequestDTO fkDepartamento;
    private MarcasRequestDTO fkMarca;
    private ProveedoresRequestDTO fkProveedor;

    
}
