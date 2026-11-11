package com.proyectoxml.mef.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(value = "Fact_Gasto_Presupuestal", schema = "mef")
public class GastoPresupuestalEntity {

    @Id
    @Column("Id_Hecho")
    private Long idHecho;

    @Column("Id_Tiempo")
    private Long idTiempo;

    @Column("Id_Entidad")
    private Long idEntidad;
    
    @Column("Id_Clasificador")
    private Long idClasificador;

    @Column("Id_Ubicacion")
    private Long idUbicacion;

    @Column("PIA")
    private BigDecimal pia;

    @Column("PIM")
    private BigDecimal pim;

    @Column("Certificado")
    private BigDecimal certificado;

    @Column("CompAnual")
    private BigDecimal compAnual;

    @Column("AtencionCompMensual")
    private BigDecimal atencionCompMensual;

    @Column("Devengado")
    private BigDecimal devengado;

    @Column("Girado")
    private BigDecimal girado;

    @Column("Id_Ejecucion")
    private Integer idEjecucion;

    @Column("Id_Url")
    private Long idUrl;
    
    @Column("Fecha_Carga")
    private LocalDateTime fechaCarga;
}
