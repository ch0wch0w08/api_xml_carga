package com.proyectoxml.job.domain.model;

import java.util.UUID;
import com.proyectoxml.job.domain.enums.ETLJobStatus;

public record ETLJob (
    UUID jobId,
    Long idEjecucion,
    ETLJobStatus status,
    Integer exitCode
){

}   