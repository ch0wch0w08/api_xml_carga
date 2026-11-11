package com.proyectoxml.job.domain.model;

import java.time.Instant;
import java.util.Map;


public record ETLEvent(
    String type,
    Instant timestamp,
    String message,
    Map<String, Object> data
) {
    public static ETLEvent log(String type, String message) {
        return new ETLEvent(
            type,
            Instant.now(),
            message,
            Map.of()
        );
    }
}