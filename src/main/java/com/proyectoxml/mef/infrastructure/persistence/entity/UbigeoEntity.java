package com.proyectoxml.mef.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "Dim_Ubigeo", schema = "mef")
public class UbigeoEntity {
    @Id
    @Column("Id_Ubicacion")
    private Long idUbicacion;

    @Column("Departamento")
    private String departamento;

    @Column("Provincia")
    private String provincia;
    
    @Column("Distrito")
    private String distrito;
}
