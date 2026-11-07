package com.proyectoxml.shared.domain.model;

import lombok.Data;
  
@Data
public class PaginationQuery {
    
    private int limit = 50;
    private String sortBy = "";       
    private String direction = "ASC";        
    private Long lastId = null;
    private Object lastSortValue = null;
    
}
