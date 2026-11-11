package com.proyectoxml.carga.infrastructure.persistence.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import io.r2dbc.spi.Row;

import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.carga.infrastructure.persistence.entity.UrlEntity;
import com.proyectoxml.carga.infrastructure.persistence.query.UrlQueryFilters;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository 
@RequiredArgsConstructor 
public class UrlSearchRepository {
    
    private final UrlQueryFilters filters;
    private final DatabaseClient databaseClient;

    public Flux<UrlEntity> search(PaginationQuery query, UrlFilter filter){

        String sortBy = validarCampoSort(query.getSortBy());
        String direction = "DESC".equalsIgnoreCase(query.getDirection()) ? "DESC" : "ASC";
        String comparison = "DESC".equals(direction) ? "<" : ">";
        

        SqlQueryBuilder builder = new SqlQueryBuilder(String.format("""
            SELECT TOP (%d) *
            FROM dbo.URLs
            WHERE 1 = 1 
        """, query.getLimit()));

        filters.apply(filter, builder);

        if(query.getLastSortValue() != null && query.getLastId() !=null){
            builder.and(String.format("""
                (
                %s %s :lastSortValue
                OR
                (%s = :lastSortValue AND Id_Url %s :lastId)
                )
            """,sortBy,comparison,sortBy,comparison))
            .param("lastSortValue", query.getLastSortValue())
            .param("lastId", query.getLastId());
        }

        String orderByClause = sortBy.equalsIgnoreCase("Id_Url") 
            ? String.format("ORDER BY Id_Url %s", direction)
            : String.format("ORDER BY %s %s, Id_Url %s", sortBy, direction, direction);

        builder.append(orderByClause);

        return databaseClient
            .sql(builder.sql())
            .bindValues(builder.params())
            .map((row, metadata) -> toUrlEntity(row))
            .all();
    }

    public Mono<Long> count(UrlFilter filter) {
        SqlQueryBuilder builder = new SqlQueryBuilder("""
            SELECT COUNT_BIG(*) AS totalItems
            FROM dbo.URLs
            WHERE 1 = 1
        """);
        filters.apply(filter, builder);
        return databaseClient.sql(builder.sql())
            .bindValues(builder.params())
            .map((row, metadata) -> row.get("totalItems", Long.class))
            .one();
    }

    private UrlEntity toUrlEntity(Row row) {
        UrlEntity entity = new UrlEntity();
        entity.setIdUrl(row.get("Id_Url", Long.class));
        entity.setUrlValue(row.get("url", String.class));
        entity.setAnio(row.get("Anio", Integer.class));
        entity.setCantidadRegistros(row.get("Cantidad_Registros", Integer.class));
        return entity;
    }

    public Flux<Integer> findDistinctYears() {
        return databaseClient.sql("SELECT DISTINCT Anio FROM dbo.URLs ORDER BY Anio DESC")
            .map((row, metadata) -> row.get("Anio", Integer.class))
            .all();
    }

    private String validarCampoSort(String field){

        if(field == null || field.isBlank()){
            return "Id_Url";
        }

        switch (field) {
            case "IdUrl", "idUrl": return "Id_Url";
            case "Url", "url", "urlValue": return "url";
            case "Anio", "anio": return "Anio";
            case "CantidadRegitros", "cantidadRegistros": return "Cantidad_Registros";
            default: return "Id_Url";
        }

    }
}
