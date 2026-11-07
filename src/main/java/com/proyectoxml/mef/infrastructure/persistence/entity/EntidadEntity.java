package com.proyectoxml.mef.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "Dim_Entidad", schema = "mef")
public class EntidadEntity {
    @Id
    @Column("Id_Entidad")
    private Long idEntidad;

    @Column("IdNivelGobierno")
    private String idNivelGobierno;

    @Column("Sub_tipo_gobierno")
    private String subTipoGobierno;

    @Column("IdSector")
    private String idSector;

    @Column("IdPliego")
    private String idPliego;

    @Column("IdEjecutora")
    private String idEjecutora;
    
    @Column("SecEjec")
    private Integer secEjec;
}
