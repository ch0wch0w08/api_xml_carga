package com.proyectoxml.mef.infrastructure.persistence.adapter;

import com.proyectoxml.mef.aplication.port.out.RepositoryMef;
import com.proyectoxml.mef.domain.model.*;
import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.mef.infrastructure.persistence.entity.ClasificadorGastoEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.EntidadEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.TiempoEntity;
import com.proyectoxml.mef.infrastructure.persistence.entity.UbigeoEntity;
import com.proyectoxml.mef.infrastructure.persistence.mapper.MefMapper;
import com.proyectoxml.mef.infrastructure.persistence.repository.ClasificadorGastoR2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.repository.EntidadR2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.repository.GastoPresupuestalR2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.repository.GastoPresupuestalSearchRepository;
import com.proyectoxml.mef.infrastructure.persistence.repository.TiempoR2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.repository.UbigeoR2dbcRepository;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository 
@RequiredArgsConstructor
public class RepositoryMefAdapter implements RepositoryMef {
    
    private final GastoPresupuestalR2dbcRepository repositoryPresupuestal;
    private final TiempoR2dbcRepository tiempoR2dbcRepository;
    private final UbigeoR2dbcRepository ubigeoR2dbcRepository;
    private final EntidadR2dbcRepository entidadR2dbcRepository;
    private final ClasificadorGastoR2dbcRepository clasificadorGastoR2dbcRepository;
    
    private final GastoPresupuestalSearchRepository repositorySearch;
    private final MefMapper mapper;

    @Override
    public Mono<GastoPresupuestal> getFactById(Long id) {
        return repositoryPresupuestal.findById(id)
            .flatMap(g -> {
                Mono<TiempoEntity> tiempoMono = tiempoR2dbcRepository.findById(g.getIdTiempo());
                Mono<ClasificadorGastoEntity> clasificadorMono = clasificadorGastoR2dbcRepository.findById(g.getIdClasificador());
                Mono<EntidadEntity> entidadMono = entidadR2dbcRepository.findById(g.getIdEntidad());
                Mono<UbigeoEntity> ubigeoMono = ubigeoR2dbcRepository.findById(g.getIdUbicacion());

                return Mono.zip(tiempoMono, clasificadorMono, entidadMono, ubigeoMono)
                    .map(tuple -> {
                        TiempoEntity tiempo = tuple.getT1();
                        ClasificadorGastoEntity clasificador = tuple.getT2();
                        EntidadEntity entidad = tuple.getT3();
                        UbigeoEntity ubigeo = tuple.getT4();

                        return mapper.toDomain(g, tiempo, clasificador, entidad, ubigeo);
                    });
            });
    }

    @Override
    public Mono<PaginationResponse<GastoPresupuestal>> getFacts(GastoPresupuestalFilter filtro, PaginationQuery query) { 
        int limitOriginal = query.getLimit();
        query.setLimit(query.getLimit() + 1);

        return Mono.zip(
            repositorySearch.search(filtro, query).map(mapper::toDomain).collectList(),
            repositorySearch.count(filtro)
        ).map(tuple -> {
                var g1 = tuple.getT1();
                boolean hasNext = g1.size() > limitOriginal;
                if(hasNext){
                    g1.remove(g1.size() - 1);
                }

                Long nextIdHecho = null;
                Object nextLastSortValue = null; 

                if(!g1.isEmpty()){
                    GastoPresupuestal lastRegister = g1.get(g1.size() - 1);
                    nextIdHecho = lastRegister.getIdHecho();
                    nextLastSortValue = extraerValorSort(lastRegister, query.getSortBy());
                }
                return new PaginationResponse<GastoPresupuestal>(
                    g1,
                    limitOriginal,
                    hasNext,
                    nextLastSortValue,
                    nextIdHecho,
                    tuple.getT2()
                );
            });
    }

    private Object extraerValorSort(GastoPresupuestal registro, String sortBy) {

        if (sortBy == null) return registro.getIdHecho();
        String campo = sortBy.toLowerCase().replace("fg.", "");

        switch (campo) {
            case "idhecho": 
                return registro.getIdHecho();
            case "idtiempo":
                return registro.getTiempo().getIdTiempo();
            case "identidad":
                return registro.getEntidad().getIdEntidad();
            case "idclasificador":
                return registro.getClasificador().getIdClasificador();
            case "idubicacion":
                return registro.getUbicacion().getIdUbicacion();
            case "pia": 
                return registro.getPia();
            case "pim": 
                return registro.getPim();
            case "certificado": 
                return registro.getCertificado();
            case "companual": 
            case "comp_anual": 
                return registro.getCompAnual();
            case "atencioncompmensual": 
                return registro.getAtencionCompMensual();
            case "devengado": 
                return registro.getDevengado();
            case "girado": 
                return registro.getGirado();
            case "idejecucion": 
                return registro.getIdEjecucion();
            case "idurl":
                return registro.getIdUrl();
            case "fechacarga": 
                return registro.getFechaCarga();
            default: 
                return registro.getIdHecho();
        }
    }

}
