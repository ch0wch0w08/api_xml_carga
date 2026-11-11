package com.proyectoxml.carga.domain.model;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import com.proyectoxml.carga.domain.exceptions.IncorrectUrlFormatException;
import com.proyectoxml.carga.domain.exceptions.InvalidValueException;
import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class Url {
    
    private Long idUrl;
    private String urlValue;
    private Integer anio;
    private Integer cantidadRegistros;  


    public static Url createUrl(String urlString, Integer cantidadRegistros) {
        try {
            URI uri = new URI(urlString);
            
            if (uri.getHost() == null || !uri.getHost().endsWith("mef.gob.pe")) {
                throw new IncorrectUrlFormatException("Dominio no válido: " + urlString);
            }

            Map<String, String> params = new HashMap<>();
            String query = uri.getQuery(); // '?'
            
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] keyValue = param.split("=", 2);
                    if (keyValue.length == 2) {
                        params.put(keyValue[0], keyValue[1]);
                    }
                }
            }

            String anoEjeParam = params.get("anoEje");
            String secEjecParam = params.get("sec_ejec");
            String nivelGobParam = params.get("nivelGobierno");

            if (anoEjeParam == null || secEjecParam == null || nivelGobParam == null) {
                throw new IncorrectUrlFormatException("Faltan parámetros obligatorios en la URL: " + urlString);
            }

            int anio = ValidateAnio(anoEjeParam);
            ValidateSecEjec(secEjecParam);
            ValidateNivelGobierno(nivelGobParam);

            return Url.builder()
                    .urlValue(urlString)
                    .anio(anio)
                    .cantidadRegistros(cantidadRegistros)
                    .build();

        } catch (Exception e) {
            throw new IncorrectUrlFormatException("Error al procesar la URL: " + urlString);
        }
    }

    private static int ValidateAnio(String anio){
        String anio_trim = anio.trim();

        try{
            int anio_num = Integer.parseInt(anio_trim);
            if(anio_num < 1900 && anio_num > LocalDate.now().getYear()){
                throw new InvalidValueException(anio);
            }
            return anio_num;
        }catch(NumberFormatException e){
            throw new NumberFormatException();
        }
    }

    private static int ValidateSecEjec(String ejecSec){
        String ejecSec_trim = ejecSec.trim();

        try{
            int ejecSec_trim_num = Integer.parseInt(ejecSec_trim);

            if(ejecSec_trim_num < 0 ){
                throw new InvalidValueException(ejecSec);
            }

            return ejecSec_trim_num;
        }catch(NumberFormatException e){
            throw new NumberFormatException();
        }  
    }

    private static String ValidateNivelGobierno(String nivelGobierno){
        String nivelGobierno_trim = nivelGobierno.trim().toUpperCase();

        if(nivelGobierno_trim.length() != 1){
            throw new InvalidValueException(nivelGobierno);
        }

        return nivelGobierno_trim;
    }

}
