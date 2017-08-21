package com.zzz.mt.mapping;

import com.zzz.mt.reflect.Reflection;
import com.zzz.mt.support.SqlType;
import com.zzz.mt.support.id.AutoCreateIdValue;
import com.zzz.mt.utils.Preconditions;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by hushengjun on 2017/8/11.
 */
public class EntityScanner {
    private Object object;
    private SqlType sqlType = SqlType.INSERT;
    private Class<?> clazz;

    public EntityScanner(Object object) {
        this.object = object;
    }

    public EntityScanner(Class<?> clazz) {
        this.clazz = clazz;
    }

    public EntityScanner(Object object, SqlType sqlType) {
        this.object = object;
        this.sqlType = sqlType;
    }

    /**
     * 扫描实体类的字段，获取其中的所有属性
     * @return
     */
    public List<PersistentProperty> scanField() {
        List<PersistentProperty> propertyList = new ArrayList<>();

        Field[] fields = Reflection.getDeclaredFields(this.clazz);
        for (Field field : fields) {
            PersistentProperty persistentProperty = new PersistentProperty();
            Annotation[] annotations = field.getAnnotations();

            Column columnAnno = field.getAnnotation(Column.class);

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            String columnName = fieldName;
            if (columnAnno != null) columnName = columnAnno.name();

            persistentProperty.setFieldName(fieldName)
                    .setAnnotations(annotations)
                    .setColumnName(columnName)
                    .setFieldType(fieldType)
                    .setField(field);

            propertyList.add(persistentProperty);
        }

        return propertyList;
    }

    /**
     * 扫描实体类中的方法
     * @return
     */
    public List<PersistentProperty> scanMethod() {
        List<PersistentProperty> propertyList = new ArrayList<>();

        Method[] methods = Reflection.getDeclaredMethods(clazz);
        for (Method method : methods) {
            PersistentProperty persistentProperty = new PersistentProperty();
            Annotation[] annotations = method.getAnnotations();

            Column columnAnno = method.getAnnotation(Column.class);
            // 没有Column注解直接跳过
            if (columnAnno == null) continue;

            String columnName = columnAnno.name();
            Class<?> fieldType = method.getReturnType();

            persistentProperty.setAnnotations(annotations)
                    .setFieldType(fieldType)
                    .setColumnName(columnName);

            propertyList.add(persistentProperty);
        }

        return propertyList;
    }

    /**
     * 扫描实体类头部就是类名那一堆东西
     * @return
     */
    public PersistentEntity scanEntityTitle() {
        PersistentEntity persistentEntity = new PersistentEntity();

        String entityName = this.clazz.getName();
        String tableName = entityName;
        Annotation[] entityAnnos = this.clazz.getAnnotations();
        Entity entity = this.clazz.getAnnotation(Entity.class);
        Preconditions.checkNotNull(entity,
                this.clazz + " is not a database entity, maybe you should add annotation '@Entity'");

        Table table = this.clazz.getAnnotation(Table.class);
        if (table != null && !"".equals(table.name())) tableName = table.name();

        persistentEntity.setEntityName(entityName)
                .setTableName(tableName)
                .setEntityAnnotation(entityAnnos);

        return persistentEntity;
    }

    /**
     * 获取组装的整个映射对象
     * @return
     */
    public PersistentEntity getPersistentEntity() {
        PersistentEntity persistentEntity = this.scanEntityTitle();
        persistentEntity.setPropertyList(this.scanField());
        return persistentEntity;
    }

}
