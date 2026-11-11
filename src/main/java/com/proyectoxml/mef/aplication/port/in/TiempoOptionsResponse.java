package com.proyectoxml.mef.aplication.port.in;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TiempoOptionsResponse {
    private List<Integer> anios;
    private List<Integer> meses;
}
