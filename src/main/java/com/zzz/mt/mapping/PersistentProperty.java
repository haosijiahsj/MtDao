package com.zzz.mt.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class PersistentProperty {
    private String fieldName;
    private String columnName;
    private Annotation[] annotations;
    private Class<?> fieldType;
    private Field field;

    public String getFieldName() {
        return fieldName;
    }

    public PersistentProperty setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public PersistentProperty setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public PersistentProperty setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
        return this;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public PersistentProperty setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
