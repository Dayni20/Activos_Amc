package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquiposRequestDTO {

    private int idEquipo;

    @NotBlank
    private String codigoSap;

    @NotBlank
    private String tipoEquipo;

    @NotBlank
    private String modelo;

    @NotBlank
    private String serial;

    @NotBlank
    private String procesador;

    private Integer memoriaRamGb;

    private Integer capacidadAlmacenamientoGb;

    @NotBlank
    private String sistemaOperativo;

    private Boolean licenciaWindowsActivada;

    private Boolean etiquetaActivoFijo;

    @NotBlank
    private String tipoLicenciaOffice;

    @NotBlank
    private String versionOffice;

    private Boolean unionDominio;

    @NotBlank
    private String ip;

    @NotBlank
    private String mac;

    private LocalDate fechaCompra;

    private BigDecimal precioCompra;

    @NotBlank
    private String estadoEquipo;

    @NotBlank
    private String observacionEquipo;

    private boolean estado;
    
    private DepartamentosRequestDTO fkDepartamento;
}
