package com.proyectoxml.mef.aplication.port.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.proyectoxml.mef.domain.model.ClasificadorGasto;
import com.proyectoxml.mef.domain.model.Entidad;
import com.proyectoxml.mef.domain.model.GastoPresupuestal;
import com.proyectoxml.mef.domain.model.Tiempo;
import com.proyectoxml.mef.domain.model.Ubigeo;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class GastoPresupuestoFullResponse {
    private Long idHecho;
    private BigDecimal pia;
    private BigDecimal pim;
    private BigDecimal certificado;
    private BigDecimal compAnual;
    private BigDecimal atencionCompMensual;
    private BigDecimal devengado;
    private BigDecimal girado;
    private Integer idEjecucion;
    private String urlOrigen;
    private LocalDateTime fechaCarga;
    
    private Tiempo tiempo;
    private Entidad entidad;
    private ClasificadorGasto clasificador;
    private Ubigeo ubicacion;


    public static GastoPresupuestoFullResponse toGastoPresupuestalFullResponse(GastoPresupuestal gastoPresupuestal){
        return GastoPresupuestoFullResponse.builder()
                .idHecho(gastoPresupuestal.getIdHecho())
                .pia(gastoPresupuestal.getPia())
                .pim(gastoPresupuestal.getPim())
                .certificado(gastoPresupuestal.getCertificado())
                .compAnual(gastoPresupuestal.getCompAnual())
                .atencionCompMensual(gastoPresupuestal.getAtencionCompMensual())
                .devengado(gastoPresupuestal.getDevengado())
                .girado(gastoPresupuestal.getGirado())
                .idEjecucion(gastoPresupuestal.getIdEjecucion())
                .urlOrigen(gastoPresupuestal.getUrlOrigen())
                .fechaCarga(gastoPresupuestal.getFechaCarga())
                .tiempo(gastoPresupuestal.getTiempo())
                .entidad(gastoPresupuestal.getEntidad())
                .clasificador(gastoPresupuestal.getClasificador())
                .ubicacion(gastoPresupuestal.getUbicacion())
                .build();
    }
}
