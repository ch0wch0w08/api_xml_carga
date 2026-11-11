package com.proyectoxml.carga.infrastructure.persistence.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;

@Component 
public class UrlQueryFilters {
    
    private final List<UrlQueryFilter> urlFilters = List.of(
        byAnio()
    );

    public void apply (UrlFilter filter, SqlQueryBuilder builder){
        urlFilters.forEach(uf -> uf.apply(filter, builder));
    }

    private UrlQueryFilter byAnio(){
        return (filter, builder) -> {
            if(filter.getAnio() == null){
                return;
            }
            builder.and(" Anio = :Anio ").param("Anio", filter.getAnio());
        };
    }

}
