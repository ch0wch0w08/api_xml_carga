package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaRechazoEntity;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AuditoriaRechazoSearchRepository {

    private final DatabaseClient databaseClient;

    public Flux<AuditoriaRechazoEntity> findByIdEjecucion(Long idEjecucion, PaginationQuery pagination) {
        String sortColumn = validateSortColumn(pagination.getSortBy());
        String direction = "DESC".equalsIgnoreCase(pagination.getDirection()) ? "DESC" : "ASC";
        String comparison = "DESC".equals(direction) ? "<" : ">";

        SqlQueryBuilder query = new SqlQueryBuilder(String.format("""
            SELECT TOP (%d) ar.*
            FROM auditoria.Auditoria_ETL_Rechazos ar
            WHERE ar.Id_Ejecucion = :idEjecucion
        """, pagination.getLimit())).param("idEjecucion", idEjecucion);

        if (pagination.getLastId() != null && pagination.getLastSortValue() != null) {
            query.and(String.format("""
                (
                    %s %s :lastSortValue
                    OR 
                    (%s = :lastSortValue AND ar.Id_Rechazo %s :lastId)
                )
                """, sortColumn, comparison, sortColumn, comparison))
                .param("lastSortValue", pagination.getLastSortValue())
                .param("lastId", pagination.getLastId());
        }

        String orderByClause = sortColumn.equalsIgnoreCase("ar.Id_Rechazo") 
            ? String.format("ORDER BY ar.Id_Rechazo %s", direction)
            : String.format("ORDER BY %s %s, ar.Id_Rechazo %s", sortColumn, direction, direction);

        query.append(orderByClause);

        return databaseClient.sql(query.sql())
            .bindValues(query.params())
            .mapProperties(AuditoriaRechazoEntity.class)
            .all();
    }

    public Mono<Long> count(Long idEjecucion) {

        return databaseClient.sql("""
            SELECT COUNT_BIG(*) AS totalItems
            FROM auditoria.Auditoria_ETL_Rechazos
            WHERE Id_Ejecucion = :idEjecucion
        """).bind("idEjecucion", idEjecucion)
            .map((row, metadata) -> row.get("totalItems", Long.class))
            .one();
    }

    private String validateSortColumn(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "ar.Id_Rechazo";
        }

        return switch (sortBy.toLowerCase().replace("ar.", "")) {
            case "idrechazo" -> "ar.Id_Rechazo";
            case "idestg" -> "ar.Id_Stg";
            case "urlorigen" -> "ar.URL_Origen";
            case "motivo" -> "ar.Motivo";
            case "fecharechazo" -> "ar.Fecha_Rechazo";
            default -> "ar.Id_Rechazo";
        };
    }
}
