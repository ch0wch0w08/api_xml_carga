package com.proyectoxml.mef.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "Dim_Tiempo", schema = "mef")
public class TiempoEntity {
    @Id
    @Column("Id_Tiempo")
    private Long idTiempo;

    @Column("AnioEje")
    private Integer anioEje;
    
    @Column("MesEje")
    private Integer mesEje;
}
