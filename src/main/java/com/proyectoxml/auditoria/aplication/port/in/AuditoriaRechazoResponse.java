package com.proyectoxml.auditoria.aplication.port.in;

import com.proyectoxml.auditoria.domain.model.Auditoria_rechazos;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditoriaRechazoResponse {
    private Long idRechazo;
    private Long idEjecucion;
    private String urlOrigen;
    private Long idStg;
    private String motivo;
    private String filaOriginal;
    private LocalDateTime fechaRechazo;

    public static AuditoriaRechazoResponse from(Auditoria_rechazos source) {
        return AuditoriaRechazoResponse.builder()
            .idRechazo(source.getIdRechazo())
            .idEjecucion(source.getIdEjecucion())
            .urlOrigen(source.getUrlOrigen())
            .idStg(source.getIdStg())
            .motivo(source.getMotivo())
            .filaOriginal(source.getFilaOriginal())
            .fechaRechazo(source.getFechaRechazo())
            .build();
    }
}
