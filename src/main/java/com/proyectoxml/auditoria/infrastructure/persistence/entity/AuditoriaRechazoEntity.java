package com.proyectoxml.auditoria.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "Auditoria_ETL_Rechazos", schema = "auditoria")
public class AuditoriaRechazoEntity {
    @Id
    @Column("Id_Rechazo")
    private Long idRechazo;

    @Column("Id_Ejecucion")
    private Integer idEjecucion;

    @Column("URL_Origen")
    private String urlOrigen;
    
    @Column("Id_Stg")
    private Long idStg;

    @Column("Motivo")
    private String motivo;

    @Column("Fila_Original")
    private String filaOriginal;
    
    @Column("Fecha_Rechazo")
    private LocalDateTime fechaRechazo;
}
