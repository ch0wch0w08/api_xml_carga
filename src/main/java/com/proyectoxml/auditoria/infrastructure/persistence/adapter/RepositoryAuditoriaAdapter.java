package com.proyectoxml.auditoria.infrastructure.persistence.adapter;

import com.proyectoxml.auditoria.aplication.port.out.RepositoryAuditoria;
import com.proyectoxml.auditoria.domain.model.Auditoria_detalle;
import com.proyectoxml.auditoria.domain.model.Auditoria_ejecucion;
import com.proyectoxml.auditoria.domain.model.Auditoria_rechazos;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.auditoria.infrastructure.persistence.mapper.AuditoriaMapper;
import com.proyectoxml.auditoria.infrastructure.persistence.repository.AuditoriaDetalleSearchRepository;
import com.proyectoxml.auditoria.infrastructure.persistence.repository.AuditoriaEjecucionSearchRepository;
import com.proyectoxml.auditoria.infrastructure.persistence.repository.AuditoriaRechazoSearchRepository;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RepositoryAuditoriaAdapter implements RepositoryAuditoria {

    private final AuditoriaMapper mapper;
    private final AuditoriaEjecucionSearchRepository ejecucionSearch;
    private final AuditoriaDetalleSearchRepository detalleSearch;
    private final AuditoriaRechazoSearchRepository rechazoSearch;

    @Override
    public Mono<PaginationResponse<Auditoria_ejecucion>> obtenerEjecuciones(AuditoriaEjecucionFilter filter,PaginationQuery query) {
        int requestedLimit = query.getLimit();
        query.setLimit(requestedLimit + 1);
        return Mono.zip(
            ejecucionSearch.search(filter, query).map(mapper::toDomain).collectList(),
            ejecucionSearch.count(filter)
        ).map(tuple -> {
                var items = tuple.getT1();
                boolean hasNext = items.size() > requestedLimit;
                if (hasNext) {
                    items.remove(items.size() - 1);
                }
                Auditoria_ejecucion last = items.isEmpty() ? null : items.get(items.size() - 1);
                return new PaginationResponse<>(
                    items,
                    requestedLimit,
                    hasNext,
                    last == null ? null : extractSortValue(last, query.getSortBy()),
                    last == null ? null : last.getIdEjecucion(),
                    tuple.getT2()
                );
            });
    }

    @Override
    public Mono<PaginationResponse<Auditoria_detalle>> obtenerDetalles(Long idEjecucion,AuditoriaDetalleFilter filter,PaginationQuery query) {
        int requestedLimit = query.getLimit();
        query.setLimit(requestedLimit + 1);
        return Mono.zip(
            detalleSearch.search(idEjecucion, filter, query).map(mapper::toDomain).collectList(),
            detalleSearch.count(idEjecucion, filter)
        ).map(tuple -> {
                var items = tuple.getT1();
                boolean hasNext = items.size() > requestedLimit;
                if (hasNext) {
                    items.remove(items.size() - 1);
                }
                Auditoria_detalle last = items.isEmpty() ? null : items.get(items.size() - 1);
                return new PaginationResponse<>(
                    items,
                    requestedLimit,
                    hasNext,
                    last == null ? null : extractSortValue(last, query.getSortBy()),
                    last == null ? null : last.getIdDetalle(),
                    tuple.getT2()
                );
            });
    }

    @Override
    public Mono<PaginationResponse<Auditoria_rechazos>> obtenerRechazos(Long idEjecucion, PaginationQuery query) {
        int requestedLimit = query.getLimit();
        query.setLimit(requestedLimit + 1);
        return Mono.zip(
            rechazoSearch.findByIdEjecucion(idEjecucion, query).map(mapper::toDomain).collectList(),
            rechazoSearch.count(idEjecucion)
        ).map(tuple -> {
                var items = tuple.getT1();
                boolean hasNext = items.size() > requestedLimit;
                if (hasNext) {
                    items.remove(items.size() - 1);
                }
                Auditoria_rechazos last = items.isEmpty() ? null : items.get(items.size() - 1);
                return new PaginationResponse<>(
                    items,
                    requestedLimit,
                    hasNext,
                    last == null ? null : extractSortValue(last, query.getSortBy()),
                    last == null ? null : last.getIdRechazo(),
                    tuple.getT2()
                );
            });
    }

    private Object extractSortValue(Auditoria_ejecucion item, String sortBy) {
        return switch (normalize(sortBy)) {
            case "fechainicio" -> item.getFechaInicio();
            case "fechafin" -> item.getFechaFin();
            case "totalprocesados" -> item.getTotalProcesados();
            case "totalerrores" -> item.getTotalErrores();
            default -> item.getIdEjecucion();
        };
    }

    private Object extractSortValue(Auditoria_detalle item, String sortBy) {
        return switch (normalize(sortBy)) {
            case "anioeje" -> item.getAnioEje();
            case "httpstatus" -> item.getHttpStatus();
            case "fechaintento" -> item.getFechaIntento();
            default -> item.getIdDetalle();
        };
    }

    private Object extractSortValue(Auditoria_rechazos item, String sortBy) {
        return switch (normalize(sortBy)) {
            case "idestg" -> item.getIdStg();
            case "urlorigen" -> item.getUrlOrigen();
            case "motivo" -> item.getMotivo();
            case "fecharechazo" -> item.getFechaRechazo();
            default -> item.getIdRechazo();
        };
    }

    private String normalize(String value) {
        return value == null ? "" : value.toLowerCase()
            .replace("ae.", "")
            .replace("ad.", "")
            .replace("ar.", "");
    }
}
