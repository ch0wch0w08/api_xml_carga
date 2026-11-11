package com.proyectoxml.mef.aplication.port.in;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.proyectoxml.mef.domain.model.GastoPresupuestal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GastoPresupuestalResponse {

    private Long idHecho;
    private BigDecimal pia;
    private BigDecimal pim;
    private BigDecimal certificado;
    private BigDecimal compAnual;
    private BigDecimal atencionCompMensual;
    private BigDecimal devengado;
    private BigDecimal girado;
    private Integer idEjecucion;
    private Long idUrl;
    private LocalDateTime fechaCarga;
    
    private Long idTiempo;
    private Long idEntidad;
    private Long idClasificador;
    private Long idUbicacion;
    
    public static GastoPresupuestalResponse toGastoPresupuestalResponse(GastoPresupuestal gastoPresupuestal){

        return GastoPresupuestalResponse.builder()
                .idHecho(gastoPresupuestal.getIdHecho())
                .pia(gastoPresupuestal.getPia())
                .pim(gastoPresupuestal.getPim())
                .certificado(gastoPresupuestal.getCertificado())
                .compAnual(gastoPresupuestal.getCompAnual())
                .atencionCompMensual(gastoPresupuestal.getAtencionCompMensual())
                .devengado(gastoPresupuestal.getDevengado())
                .girado(gastoPresupuestal.getGirado())
                .idEjecucion(gastoPresupuestal.getIdEjecucion())
                .idUrl(gastoPresupuestal.getIdUrl())
                .fechaCarga(gastoPresupuestal.getFechaCarga())
                .idTiempo(gastoPresupuestal.getTiempo().getIdTiempo())
                .idEntidad(gastoPresupuestal.getEntidad().getIdEntidad())
                .idClasificador(gastoPresupuestal.getClasificador().getIdClasificador())
                .idUbicacion(gastoPresupuestal.getUbicacion().getIdUbicacion())
                .build();
    }

}
