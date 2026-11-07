package com.proyectoxml.auditoria.aplication.port.in;

import com.proyectoxml.auditoria.domain.model.Auditoria_detalle;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditoriaDetalleResponse {
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

    public static AuditoriaDetalleResponse from(Auditoria_detalle source) {
        return AuditoriaDetalleResponse.builder()
            .idDetalle(source.getIdDetalle())
            .idEjecucion(source.getIdEjecucion())
            .url(source.getUrl())
            .anioEje(source.getAnioEje())
            .estadoUrl(source.getEstadoUrl())
            .registrosEsperados(source.getRegistrosEsperados())
            .registrosRecibidos(source.getRegistrosRecibidos())
            .registrosInsertados(source.getRegistrosInsertados())
            .mensajeError(source.getMensajeError())
            .httpStatus(source.getHttpStatus())
            .intentos(source.getIntentos())
            .duracionMs(source.getDuracionMs())
            .bytesDescargados(source.getBytesDescargados())
            .tipoError(source.getTipoError())
            .hashContenido(source.getHashContenido())
            .fechaIntento(source.getFechaIntento())
            .build();
    }
}
