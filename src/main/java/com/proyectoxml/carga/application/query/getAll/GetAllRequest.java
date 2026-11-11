package com.proyectoxml.carga.application.query.getAll;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor 
@Data 
public class GetAllRequest implements Request<PaginationResponse<UrlResponse>>{
    private PaginationQuery query;
    private UrlFilter filter;    
}
