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
        private long totalItems;
        private int totalPages;
    */
   
    private List<T> content;
    private int limit;
    private boolean hasNext;
    private Object nextLastSortValue;
    private Long nextLastId;
        
}
