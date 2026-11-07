package com.proyectoxml.mef.domain.model.pagination;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GastoPresupuestalFilter {
    Integer anioEje;
    Integer mesEje;
    Integer idEntidad;
    Integer idClasificador;
    Integer idUbicacion;
    Integer idEjecucion;
}
