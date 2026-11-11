package com.proyectoxml.carga.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data 
@Table (name = "URLs", schema = "dbo")
public class UrlEntity {
    
    @Id
    @Column (value = "id_Url")
    private Long idUrl;

    @Column (value = "URL")
    private String urlValue;

    @Column (value = "Anio")
    private Integer anio;

    @Column (value = "Cantidad_Registros")
    private Integer cantidadRegistros; 

}
