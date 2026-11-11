package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaDetalleEntity;
import com.proyectoxml.auditoria.infrastructure.persistence.query.AuditoriaQueryFilters;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AuditoriaDetalleSearchRepository {

    private final DatabaseClient databaseClient;
    private final AuditoriaQueryFilters filters;

    public Flux<Integer> findDistinctAnios() {
        return databaseClient.sql("""
            SELECT DISTINCT AnioEje
            FROM auditoria.Auditoria_ETL_Detalle
            WHERE AnioEje IS NOT NULL
            ORDER BY AnioEje DESC
            """)
            .map((row, metadata) -> row.get("AnioEje", Integer.class))
            .all();
    }

    public Flux<AuditoriaDetalleEntity> search(
        Long idEjecucion,
        AuditoriaDetalleFilter filter,
        PaginationQuery pagination
    ) {
        String sortColumn = validateSortColumn(pagination.getSortBy());
        String direction = "DESC".equalsIgnoreCase(pagination.getDirection()) ? "DESC" : "ASC";
        String comparison = "DESC".equals(direction) ? "<" : ">";

        SqlQueryBuilder query = new SqlQueryBuilder(String.format("""
            SELECT TOP (%d) ad.*
            FROM auditoria.Auditoria_ETL_Detalle ad
            WHERE ad.Id_Ejecucion = :idEjecucion
        """, pagination.getLimit())).param("idEjecucion", idEjecucion);
    
        filters.apply(filter, query);

        if (pagination.getLastSortValue() != null && pagination.getLastId() != null) {
            query.and(String.format("""
                (
                    %s %s :lastSortValue
                    OR (%s = :lastSortValue AND ad.Id_Detalle %s :lastId)
                )
                """, sortColumn, comparison, sortColumn, comparison))
                .param("lastSortValue", pagination.getLastSortValue())
                .param("lastId", pagination.getLastId());
        }

        String orderByClause = sortColumn.equalsIgnoreCase("ad.Id_Detalle")
            ? String.format("ORDER BY ad.Id_Detalle %s", direction)
            : String.format("ORDER BY %s %s, ad.Id_Detalle %s", sortColumn, direction, direction);

        query.append(orderByClause);

        return databaseClient.sql(query.sql())
            .bindValues(query.params())
            .mapProperties(AuditoriaDetalleEntity.class)
            .all();
    }

    public Mono<Long> count(Long idEjecucion, AuditoriaDetalleFilter filter) {

        SqlQueryBuilder query = new SqlQueryBuilder("""
            SELECT COUNT_BIG(*) AS totalItems
            FROM auditoria.Auditoria_ETL_Detalle ad
            WHERE ad.Id_Ejecucion = :idEjecucion
        """).param("idEjecucion", idEjecucion);

        filters.apply(filter, query);
        return databaseClient
            .sql(query.sql())
            .bindValues(query.params())
            .map((row, metadata) -> row.get("totalItems", Long.class))
            .one();
    }

    private String validateSortColumn(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) return "ad.Id_Detalle";
        
        return switch (sortBy.toLowerCase().replace("ad.", "")) {
            case "iddetalle" -> "ad.Id_Detalle";
            case "anioeje" -> "ad.AnioEje";
            case "httpstatus" -> "ad.Http_Status";
            case "fechaintento" -> "ad.Fecha_Intento";
            default -> "ad.Id_Detalle";
        };
    }
}
