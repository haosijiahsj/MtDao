package com.zzz.mt.mapping;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class PersistentEntity {
    private String entityName;
    private String tableName;
    private Annotation[] entityAnnotation;
    private List<PersistentProperty> propertyList;

    public String getEntityName() {
        return entityName;
    }

    public PersistentEntity setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public PersistentEntity setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Annotation[] getEntityAnnotation() {
        return entityAnnotation;
    }

    public PersistentEntity setEntityAnnotation(Annotation[] entityAnnotation) {
        this.entityAnnotation = entityAnnotation;
        return this;
    }

    public List<PersistentProperty> getPropertyList() {
        return propertyList;
    }

    public PersistentEntity setPropertyList(List<PersistentProperty> propertyList) {
        this.propertyList = propertyList;
        return this;
    }
}
