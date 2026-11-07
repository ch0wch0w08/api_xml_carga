package com.proyectoxml.mef.domain.model;

import lombok.Data;

@Data
public class Entidad {
    private Long idEntidad;
    private String idNivelGobierno;
    private String subTipoGobierno;
    private String idSector;
    private String idPliego;
    private String idEjecutora;
    private Integer secEjec;
}
