package com.proyectoxml.carga.application.query.getAll;

import org.springframework.stereotype.Service;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service("cargaGetAllHandler")
@RequiredArgsConstructor
public class GetAllHandler implements RequestHandler<GetAllRequest, PaginationResponse<UrlResponse>> {

    private final UrlRepository repository;

    @Override
    public Mono<PaginationResponse<UrlResponse>> handler(GetAllRequest request) {
        return repository.getAll(request.getQuery(), request.getFilter())
            .map(page -> new PaginationResponse<>(
                page.getContent().stream().map(UrlResponse::toUrlResponse).toList(),
                page.getLimit(),
                page.isHasNext(),
                page.getNextLastSortValue(),
                page.getNextLastId(),
                page.getTotalItems()
            ));
    }

    @Override
    public Class<GetAllRequest> getRequestType() {
        return GetAllRequest.class;
    }
}
