package com.hhssjj.mt.mapping;

import com.hhssjj.mt.reflect.Reflection;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.support.id.AutoCreateIdValue;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hushengjun on 2017/8/15.
 */
public class EntityMapper {

    private Object object;
    private SqlType sqlType;

    private PersistentEntity persistentEntity;

    public EntityMapper(Object object) {
        this.object = object;
        this.persistentEntity = new EntityScanner(object.getClass()).getPersistentEntity();
    }

    public EntityMapper(Class<?> clazz) {
        this.persistentEntity = new EntityScanner(clazz).getPersistentEntity();
    }

    /**
     * 获取实体类中列对应的值
     * @return
     */
    private List<MapperColumnResult> handlePersistentEntityForValue() {
        List<PersistentProperty> propertyList = this.persistentEntity.getPropertyList();
        List<MapperColumnResult> mapperColumnResultList = new ArrayList<>();
        int i = 0;
        label : for (PersistentProperty prop : propertyList) {
            MapperColumnResult mapperColumnResult = new MapperColumnResult();

            Annotation[] annos = prop.getAnnotations();
            Field field = prop.getField();
            // 列名默认为字段名称，可以不写Column注解，但最好还是写上
            String columnName = field.getName();

            Object value = Reflection.get(object, field);
            for (Annotation anno : annos) {
                Class<? extends Annotation> annoType = anno.annotationType();

                if (Id.class.equals(anno.annotationType())) {
                    // 判断是不是id字段
                    mapperColumnResult.setId(true);
                } else if (Column.class.equals(annoType)) {
                    Column column = (Column) anno;
                    // 获取字段名称
                    if (!column.nullable()) throw new IllegalArgumentException(column.name() + "can not be null");
                    if (SqlType.INSERT.equals(sqlType) && !column.insertable()) continue label;
                    if (SqlType.UPDATE.equals(sqlType) && !column.updatable()) continue label;
                    columnName = column.name();
                } else if (GeneratedValue.class.equals(annoType)) {
                    GeneratedValue generatedValue = (GeneratedValue) anno;
                    // 获取id生成的值
                    AutoCreateIdValue idValue = new AutoCreateIdValue(generatedValue);
                    value = idValue.createId();
                    // 如果是为了生成插入类型的语句，且通过id策略获取的值为null时
                    if (SqlType.INSERT.equals(sqlType) && value == null) continue label;
                } else if (Enumerated.class.equals(annoType)) {
                    Enumerated enumerated = (Enumerated) anno;
                    // 如果是枚举类型，则重新赋值
                    Enum enumValue = (Enum) value;
                    value = EnumType.STRING.equals(enumerated.value()) ? enumValue.name() : enumValue.ordinal();
                } else if (Transient.class.equals(annoType)) {
                    // 如果有该注解，则直接跳到最外层循环
                    continue label;
                } else if (OneToOne.class.equals(annoType)) {

                } else if (OneToMany.class.equals(annoType)) {

                } else if (JoinColumn.class.equals(annoType)) {}
            }

            mapperColumnResult.setIndex(++i);
            mapperColumnResult.setColumnName(columnName);
            mapperColumnResult.setValue(value);

            mapperColumnResultList.add(mapperColumnResult);
        }

        return mapperColumnResultList;
    }

    public MapperResult getMapperResult() {
        MapperResult mapperResult = new MapperResult();
        mapperResult.setEntityName(persistentEntity.getEntityName());
        mapperResult.setTableName(persistentEntity.getTableName());
        mapperResult.setMapperColumnResults(this.handlePersistentEntityForValue());

        return mapperResult;
    }

}
