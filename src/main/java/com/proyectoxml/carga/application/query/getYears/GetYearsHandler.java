package com.proyectoxml.carga.application.query.getYears;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service("cargaGetYearsHandler")
@RequiredArgsConstructor
public class GetYearsHandler implements RequestHandler<GetYearsRequest, List<Integer>> {

    private final UrlRepository repository;

    @Override
    public Mono<List<Integer>> handler(GetYearsRequest request) {
        return repository.getYears().collectList();
    }

    @Override
    public Class<GetYearsRequest> getRequestType() {
        return GetYearsRequest.class;
    }
}
