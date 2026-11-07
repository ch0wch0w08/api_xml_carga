package com.proyectoxml.auditoria.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Auditoria_detalle {
    private Long idDetalle;
    private Long idEjecucion;
    private String url;
    private Integer anioEje;
    private String estadoUrl;
    private Integer registrosEsperados;
    private Integer registrosRecibidos;
    private Integer registrosInsertados;
    private String mensajeError;
    private Integer httpStatus;
    private Integer intentos;
    private Integer duracionMs;
    private Long bytesDescargados;
    private String tipoError;
    private String hashContenido;
    private LocalDateTime fechaIntento;
}
