package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaEjecucionEntity;
import com.proyectoxml.auditoria.infrastructure.persistence.query.AuditoriaQueryFilters;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class AuditoriaEjecucionSearchRepository {

    private final DatabaseClient databaseClient;
    private final AuditoriaQueryFilters filters;

    public Flux<AuditoriaEjecucionEntity> search(AuditoriaEjecucionFilter filter, PaginationQuery pagination) {
        String sortColumn = validateSortColumn(pagination.getSortBy());
        String direction = "DESC".equalsIgnoreCase(pagination.getDirection()) ? "DESC" : "ASC";
        String comparison = "DESC".equals(direction) ? "<" : ">";

        SqlQueryBuilder query = new SqlQueryBuilder("""
            SELECT ae.*
            FROM auditoria.Auditoria_ETL_Ejecucion ae
            WHERE 1 = 1
            """);
        filters.apply(filter, query);

        if (pagination.getLastSortValue() != null && pagination.getLastId() != null) {
            query.and(String.format("""
                (
                    %s %s :lastSortValue
                    OR 
                    (%s = :lastSortValue AND ae.Id_Ejecucion > :lastId)
                )
                """, sortColumn, comparison, sortColumn))
                .param("lastSortValue", pagination.getLastSortValue())
                .param("lastId", pagination.getLastId());
        }

        String orderByClause = sortColumn.equalsIgnoreCase("ae.Id_Ejecucion") 
            ? String.format("ORDER BY ae.Id_Ejecucion %s", direction)
            : String.format("ORDER BY %s %s, ae.Id_Ejecucion ASC", sortColumn, direction);

        query.append(String.format("""
            %s
            OFFSET 0 ROWS 
            FETCH NEXT %d ROWS ONLY
            """, orderByClause, pagination.getLimit()));

        return databaseClient.sql(query.sql())
            .bindValues(query.params())
            .mapProperties(AuditoriaEjecucionEntity.class)
            .all();
    }

    private String validateSortColumn(String sortBy) {
        if (sortBy == null) {
            return "ae.Id_Ejecucion";
        }
        return switch (sortBy.toLowerCase().replace("ae.", "")) {
            case "idejecucion" -> "ae.Id_Ejecucion";
            case "fechainicio" -> "ae.Fecha_Inicio";
            case "fechafin" -> "ae.Fecha_Fin";
            case "totalprocesados" -> "ae.Total_Procesados";
            case "totalerrores" -> "ae.Total_Errores";
            default -> "ae.Id_Ejecucion";
        };
    }
}
