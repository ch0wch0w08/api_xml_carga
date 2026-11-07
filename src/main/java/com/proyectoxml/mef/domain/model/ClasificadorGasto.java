package com.proyectoxml.mef.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasificadorGasto {
    private Long idClasificador;
    private Integer categGasto;
    private Integer tipoTransaccion;
    private Integer generica;
    private Integer subgenerica;
    private Integer subgenericaDet;
    private Integer especifica;
    private Integer especificaDet;
}
