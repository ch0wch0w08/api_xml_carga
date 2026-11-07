package com.proyectoxml.auditoria.aplication.port.in;

import com.proyectoxml.auditoria.domain.model.Auditoria_ejecucion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditoriaEjecucionResponse {
    private Long idEjecucion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estadoEjecucion;
    private Integer totalProcesados;
    private Integer totalErrores;
    private String observaciones;

    public static AuditoriaEjecucionResponse from(Auditoria_ejecucion source) {
        return AuditoriaEjecucionResponse.builder()
            .idEjecucion(source.getIdEjecucion())
            .fechaInicio(source.getFechaInicio())
            .fechaFin(source.getFechaFin())
            .estadoEjecucion(source.getEstadoEjecucion())
            .totalProcesados(source.getTotalProcesados())
            .totalErrores(source.getTotalErrores())
            .observaciones(source.getObservaciones())
            .build();
    }
}
