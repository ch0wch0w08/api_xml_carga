package com.proyectoxml.mef.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class GastoPresupuestal {
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
    
    @Builder.Default
    private Tiempo tiempo = new Tiempo();

    @Builder.Default
    private Entidad entidad = new Entidad();

    @Builder.Default
    private ClasificadorGasto clasificador = new ClasificadorGasto();

    @Builder.Default
    private Ubigeo ubicacion = new Ubigeo();

}
