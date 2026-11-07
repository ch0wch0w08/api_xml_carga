package com.proyectoxml.auditoria.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "Auditoria_ETL_Ejecucion", schema = "auditoria")
public class AuditoriaEjecucionEntity {
    @Id
    @Column("Id_Ejecucion")
    private Integer idEjecucion;

    @Column("Fecha_Inicio")
    private LocalDateTime fechaInicio;

    @Column("Fecha_Fin")
    private LocalDateTime fechaFin;

    @Column("Estado_Ejecucion")
    private String estadoEjecucion;

    @Column("Total_Procesados")
    private Integer totalProcesados;

    @Column("Total_Errores")
    private Integer totalErrores;
    
    @Column("Observaciones")
    private String observaciones;
}
