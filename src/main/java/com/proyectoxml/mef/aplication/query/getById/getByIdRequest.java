package com.proyectoxml.mef.aplication.query.getById;

import com.proyectoxml.mef.aplication.port.in.GastoPresupuestoFullResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class getByIdRequest implements Request<GastoPresupuestoFullResponse> {
    private Long id;
}
