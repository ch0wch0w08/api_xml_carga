package com.proyectoxml.auditoria.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Auditoria_ejecucion {
    private Long idEjecucion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estadoEjecucion;
    private Integer totalProcesados;
    private Integer totalErrores;
    private String observaciones;
}
