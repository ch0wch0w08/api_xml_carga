package com.proyectoxml.mef.infrastructure.persistence.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.mef.infrastructure.persistence.entity.GastoPresupuestalEntity;
import com.proyectoxml.mef.infrastructure.persistence.query.GastoPresupuestalQueryFilters;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor 
@Repository 
public class GastoPresupuestalSearchRepository {
    
    private final DatabaseClient databaseClient;
    private final GastoPresupuestalQueryFilters filters;   

    public Flux<GastoPresupuestalEntity> search(GastoPresupuestalFilter filter, PaginationQuery pagination) {
    
        String sortBy = validarCampoSort(pagination.getSortBy()); 
        String direction = "DESC".equalsIgnoreCase(pagination.getDirection()) ? "DESC" : "ASC";
        String comparison = "DESC".equals(direction) ? "<" : ">";

        SqlQueryBuilder query = new SqlQueryBuilder(String.format(
        """
            SELECT TOP (%d) fg.*
            FROM mef.Fact_Gasto_Presupuestal fg
            WHERE 1 = 1 
        """, pagination.getLimit()));

        filters.apply(filter, query);

        if (pagination.getLastSortValue() != null && pagination.getLastId() != null) {
            query.and(String.format("""
                (
                    %s %s :lastSortValue  
                    OR
                    (%s = :lastSortValue AND fg.Id_Hecho %s :lastId)
                )
            """, sortBy, comparison, sortBy, comparison))
            .param("lastSortValue", pagination.getLastSortValue())
            .param("lastId", pagination.getLastId());
        }

        String orderByClause = sortBy.equalsIgnoreCase("fg.Id_Hecho") 
            ? String.format("ORDER BY fg.Id_Hecho %s", direction)
            : String.format("ORDER BY %s %s, fg.Id_Hecho %s", sortBy, direction, direction);

        /* 
        query.append(String.format("""
                %s
                OFFSET 0 ROWS 
                FETCH NEXT %d ROWS ONLY
            """, orderByClause, pagination.getLimit()));
        */
        
        query.append(orderByClause);

        return databaseClient
            .sql(query.sql())
            .bindValues(query.params())
            .mapProperties(GastoPresupuestalEntity.class)
            .all();
    }

    public Mono<Long> count(GastoPresupuestalFilter filter){

        SqlQueryBuilder query = new SqlQueryBuilder("""
            SELECT COUNT(*)
            FROM mef.Fact_Gasto_Presupuestal fg
            WHERE 1 = 1 
        """);

        filters.apply(filter, query);
        
        return databaseClient
            .sql(query.sql())
            .bindValues(query.params())
            .mapValue(Long.class)
            .one();

    }

    private String validarCampoSort(String sortBy) {

        if (sortBy == null || sortBy.isBlank()) return "fg.Id_Hecho";
        String campo = sortBy.toLowerCase().replace("fg.", "");

        switch (campo) {
            case "idhecho": return "fg.Id_Hecho";
            case "idtiempo": return "fg.Id_Tiempo";
            case "identidad": return "fg.Id_Entidad";
            case "idclasificador": return "fg.Id_Clasificador";
            case "idubicacion": return "fg.Id_Ubicacion";
            case "pia": return "fg.PIA";
            case "pim": return "fg.PIM";
            case "certificado": return "fg.Certificado";
            case "companual": return "fg.CompAnual";
            case "atencioncompmensual": return "fg.AtencionCompMensual";
            case "devengado": return "fg.Devengado";
            case "girado": return "fg.Girado";
            case "idejecucion": return "fg.Id_Ejecucion";
            case "idurl": return "fg.Id_Url";
            case "fechacarga": return "fg.Fecha_Carga";
            default: return "fg.Id_Hecho";
        }

    }

}
