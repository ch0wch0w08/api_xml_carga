package com.proyectoxml.auditoria.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Auditoria_rechazos {
    private Long idRechazo;
    private Long idEjecucion;
    private String urlOrigen;
    private Long idStg;
    private String motivo;
    private String filaOriginal;
    private LocalDateTime fechaRechazo;
}
