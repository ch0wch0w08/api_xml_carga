package com.proyectoxml.carga.application.query.getYears;

import java.util.List;

import com.proyectoxml.shared.infrastructure.mediator.Request;

public record GetYearsRequest() implements Request<List<Integer>> {
}
