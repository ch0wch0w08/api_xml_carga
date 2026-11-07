package com.proyectoxml.mef.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "Dim_Clasificador_Gasto", schema = "mef")
public class ClasificadorGastoEntity {
    @Id
    @Column("Id_Clasificador")
    private Long idClasificador;

    @Column("Categ_gasto")
    private Integer categGasto;

    @Column("Tipo_transaccion")
    private Integer tipoTransaccion;

    @Column("Generica")
    private Integer generica;

    @Column("Subgenerica")
    private Integer subgenerica;

    @Column("Subgenerica_det")
    private Integer subgenericaDet;

    @Column("Especifica")
    private Integer especifica;
    
    @Column("Especifica_det")
    private Integer especificaDet;
}
