package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquiposRequestDTO {

    @NotBlank
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

    @NotBlank
    private Integer memoriaRamGb;

    @NotBlank
    private Integer capacidadAlmacenamientoGb;

    @NotBlank
    private String sistemaOperativo;

    @NotBlank
    private Boolean licenciaWindowsActivada;

    @NotBlank
    private Boolean etiquetaActivoFijo;

    @NotBlank
    private String tipoLicenciaOffice;

    @NotBlank
    private String versionOffice;

    @NotBlank
    private Boolean unionDominio;

    @NotBlank
    private String ip;

    @NotBlank
    private String mac;

    @NotBlank
    private LocalDate fechaCompra;

    @NotBlank
    private BigDecimal precioCompra;

    @NotBlank
    private String estadoEquipo;

    @NotBlank
    private String observacionEquipo;

    @NotBlank
    private String estado;
    
    private DepartamentosRequestDTO fkDepartamento;
}
