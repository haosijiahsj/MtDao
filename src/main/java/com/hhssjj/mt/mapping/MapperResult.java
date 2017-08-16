package com.hhssjj.mt.mapping;

import java.util.List;

/**
 * Created by hushengjun on 2017/8/16.
 */
public class MapperResult {
    private String tableName;
    private String entityName;
    private List<MapperColumnResult> mapperColumnResults;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<MapperColumnResult> getMapperColumnResults() {
        return mapperColumnResults;
    }

    public void setMapperColumnResults(List<MapperColumnResult> mapperColumnResults) {
        this.mapperColumnResults = mapperColumnResults;
    }
}
