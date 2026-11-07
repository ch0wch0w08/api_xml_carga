package com.proyectoxml.mef.infrastructure.persistence.mapper;

import com.proyectoxml.mef.domain.model.ClasificadorGasto;
import com.proyectoxml.mef.domain.model.Entidad;
import com.proyectoxml.mef.domain.model.GastoPresupuestal;
import com.proyectoxml.mef.domain.model.Tiempo;
import com.proyectoxml.mef.domain.model.Ubigeo;
import com.proyectoxml.mef.infrastructure.persistence.entity.ClasificadorGastoEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.EntidadEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.GastoPresupuestalEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.TiempoEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.UbigeoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MefMapper {

    Tiempo toDomain(TiempoEntity source);

    Entidad toDomain(EntidadEntity source);

    ClasificadorGasto toDomain(ClasificadorGastoEntity source);

    Ubigeo toDomain(UbigeoEntity source);

    @Mapping (source = "idTiempo", target = "tiempo.idTiempo")
    @Mapping (source = "idEntidad", target = "entidad.idEntidad")
    @Mapping (source = "idClasificador", target = "clasificador.idClasificador")
    @Mapping (source = "idUbicacion", target = "ubicacion.idUbicacion")
    GastoPresupuestal toDomain (GastoPresupuestalEntity source);


    @Mapping(source = "tiempo", target = "tiempo")
    @Mapping(source = "clasificador", target = "clasificador")
    @Mapping(source = "entidad", target = "entidad")
    @Mapping(source = "ubigeo", target = "ubicacion")
    GastoPresupuestal toDomain(
        GastoPresupuestalEntity source,
        TiempoEntity tiempo,
        ClasificadorGastoEntity clasificador,
        EntidadEntity entidad,
        UbigeoEntity ubigeo
    );

}
