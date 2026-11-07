package com.proyectoxml.mef.infrastructure.persistence.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;

@Component 
public class GastoPresupuestalQueryFilters {

    private List<GastoPresupuestalQueryFilter> Filters = List.of(
        byAnio(),
        byIdClasificador(),
        byIdEntidad(),
        byIdUbicacion(),
        byMes()
    );

    public void apply (GastoPresupuestalFilter filter, SqlQueryBuilder builder){
        Filters.forEach(f -> f.apply(filter, builder));
    }

    private GastoPresupuestalQueryFilter byAnio(){
        return (filter, builder) -> {
            if(filter.getAnioEje() == null){
                return; 
            }
            builder.and("""
                    EXISTS (
                        SELECT 1 
                        FROM mef.Dim_Tiempo dt 
                        WHERE dt.AnioEje = :AnioEje
                    )
            """).param("AnioEje", filter.getAnioEje());
        };
    }

    private GastoPresupuestalQueryFilter byMes(){
        return (filter, builder) -> {
            if(filter.getMesEje() == null){
                return; 
            }
            builder.and("""
                    EXISTS (
                        SELECT 1 
                        FROM mef.Dim_Tiempo dt 
                        WHERE dt.MesEje = :MesEje
                    )
            """).param("MesEje", filter.getMesEje());
        };
    }

    private GastoPresupuestalQueryFilter byIdEntidad(){
        return (filter, builder) -> {
            if(filter.getIdEntidad() == null){
                return; 
            }
            builder.and(" Id_Entidad = :Id_Entidad").param("Id_Entidad", filter.getIdEntidad());
        };
    }

    private GastoPresupuestalQueryFilter byIdClasificador(){
        return (filter, builder) -> {
            if(filter.getIdClasificador() == null){
                return; 
            }
            builder.and("Id_Clasificador = :Id_Clasificador").param("Id_Clasificador", filter.getIdClasificador());
        };
    }


    private GastoPresupuestalQueryFilter byIdUbicacion(){
        return (filter, builder) -> {
            if(filter.getIdUbicacion() == null){
                return; 
            }
            builder.and("Id_Ubicacion = :Id_Ubicacion").param("Id_Ubicacion", filter.getIdClasificador());
        };
    }

}
