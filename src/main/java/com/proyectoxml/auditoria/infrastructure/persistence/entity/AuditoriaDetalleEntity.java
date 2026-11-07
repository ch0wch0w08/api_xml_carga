package com.proyectoxml.auditoria.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "Auditoria_ETL_Detalle", schema = "auditoria")
public class AuditoriaDetalleEntity {
    @Id
    @Column("Id_Detalle")
    private Integer idDetalle;

    @Column("Id_Ejecucion")
    private Integer idEjecucion;

    @Column("URL")
    private String url;

    @Column("AnioEje")
    private Integer anioEje;

    @Column("Estado_URL")
    private String estadoUrl;
    
    @Column("Registros_Esperados")
    private Integer registrosEsperados;

    @Column("Registros_Recibidos")
    private Integer registrosRecibidos;

    @Column("Registros_Insertados")
    private Integer registrosInsertados;

    @Column("Mensaje_Error")
    private String mensajeError;

    @Column("Http_Status")
    private Integer httpStatus;

    @Column("Intentos")
    private Integer intentos;

    @Column("Duracion_ms")
    private Integer duracionMs;

    @Column("Bytes_Descargados")
    private Long bytesDescargados;

    @Column("Tipo_Error")
    private String tipoError;

    @Column("Hash_Contenido")
    private String hashContenido;
    
    @Column("Fecha_Intento")
    private LocalDateTime fechaIntento;
}
