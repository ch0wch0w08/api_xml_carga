package com.proyectoxml.auditoria.domain.model.pagination;

import lombok.Data;

@Data
public class AuditoriaDetalleFilter {
    private Integer anioEje;
    private String estadoUrl;
    private Integer httpStatus;
}
