package com.uisrael.gestionactivosapi.presentacion.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquiposRequestDTO {

    @NotBlank
    private int id_equipo;

    @NotBlank
    private String codigo_sap;

    @NotBlank
    private int id_categoria;

    @NotBlank
    private int id_marca;

    @NotBlank
    private int id_proveedor;

    @NotBlank
    private String tipo_equipo;

    @NotBlank
    private String modelo;

    @NotBlank
    private String serial;

    @NotBlank
    private String procesador;

    @NotBlank
    private Integer memoria_ram_gb;

    @NotBlank
    private Integer capacidad_almacenamiento_gb;

    @NotBlank
    private String sistema_operativo;

    @NotBlank
    private Boolean licencia_windows_activada;

    @NotBlank
    private Boolean etiqueta_activo_fijo;

    @NotBlank
    private String tipo_licencia_office;

    @NotBlank
    private String version_office;

    @NotBlank
    private Boolean union_dominio;

    @NotBlank
    private String ip;

    @NotBlank
    private String mac;

    @NotBlank
    private Integer id_departamento;

    @NotBlank
    private LocalDate fecha_compra;

    @NotBlank
    private BigDecimal precio_compra;

    @NotBlank
    private String estado_equipo;

    @NotBlank
    private String observacion_equipo;

    @NotBlank
    private String estado;
}
