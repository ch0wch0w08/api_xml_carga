package com.proyectoxml.shared.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class PaginationResponse <T>{

    /* 
    Enfoque antiguo
        private List<T> content;
        private int size;
        private int page;
        private Long totalItems;
        private int totalPages;
    */
   
    private List<T> content;
    private int limit;
    private boolean hasNext;
    private Object nextLastSortValue;
    private Long nextLastId;
    private Long totalItems;

    public PaginationResponse(
        List<T> content,
        int limit,
        boolean hasNext,
        Object nextLastSortValue,
        Long nextLastId
    ) {
        this(content, limit, hasNext, nextLastSortValue, nextLastId, null);
    }
        
}
