package com.proyectoxml.shared.infrastructure.query;

import java.util.HashMap;
import java.util.Map;

public class SqlQueryBuilder {
    
    private final StringBuilder sql;
    private final Map<String,Object> params = new HashMap<>();


    public SqlQueryBuilder(String baseSql) {
        this.sql = new StringBuilder(baseSql);
    }

    public SqlQueryBuilder and(String condition){
        sql.append("\nAND ").append(condition);
        return this;
    }

    public SqlQueryBuilder param(String name, Object value){
        params.put(name, value);
        return this;
    }

    public SqlQueryBuilder append(String sqlFragment){
        sql.append(sqlFragment);
        return this;
    }

    public String sql(){
        return sql.toString();
    }

    public Map<String,Object> params(){
        return params;
    }

}
