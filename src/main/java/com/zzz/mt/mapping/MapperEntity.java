package com.zzz.mt.mapping;

import java.util.List;

/**
 * Created by 胡胜钧 on 9/3 0003.
 */
public class MapperEntity {
    private String entityName;
    private String tableName;
    private MapperKey key;
    private List<MapperColumn> columns;
    private List<MapperForeignKey> foreignKeys;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public MapperKey getKey() {
        return key;
    }

    public void setKey(MapperKey key) {
        this.key = key;
    }

    public List<MapperColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<MapperColumn> columns) {
        this.columns = columns;
    }

    public List<MapperForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<MapperForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}
