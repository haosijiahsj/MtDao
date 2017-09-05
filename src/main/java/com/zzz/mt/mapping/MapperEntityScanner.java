package com.zzz.mt.mapping;

import com.google.common.collect.Lists;
import com.zzz.mt.reflect.Reflection;
import com.zzz.mt.support.AssociationType;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by 胡胜钧 on 9/3 0003.
 */
public class MapperEntityScanner {
    private Class<?> clazz;

    public MapperEntityScanner(Class<?> clazz) {
        this.clazz = clazz;
    }

    private MapperEntity scanEntityTitle() {
        MapperEntity mapperEntity = new MapperEntity();

        String entityName = this.clazz.getName();
        Table table = this.clazz.getAnnotation(Table.class);

        mapperEntity.setEntityName(entityName);
        mapperEntity.setTableName(table.name());

        return mapperEntity;
    }

    public MapperEntity scanEntity() {
        List<MapperColumn> columns = Lists.newArrayList();
        MapperKey key = new MapperKey();
        List<MapperForeignKey> foreignKeys = Lists.newArrayList();

        Field[] fields = Reflection.getDeclaredFields(this.clazz);
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            Transient transientAnno = field.getAnnotation(Transient.class);
            Id idAnno = field.getAnnotation(Id.class);
            Column columnAnno = field.getAnnotation(Column.class);
            JoinColumn joinColumnAnno = field.getAnnotation(JoinColumn.class);

            // 该注解表示临时字段
            if (transientAnno != null) continue;
            // 注解注解处理
            if (idAnno != null) {
                key.setFiledName(field.getName());
                key.setField(field);
                key.setFieldType(fieldType);
                GeneratedValue generatedValueAnno = field.getAnnotation(GeneratedValue.class);
                if (generatedValueAnno != null) {
                    key.setGenerationType(generatedValueAnno.strategy());
                } else {
                    key.setGenerationType(GenerationType.AUTO);
                }
                if (columnAnno != null) {
                    key.setColumnName(columnAnno.name());
                    key.setInsertable(columnAnno.insertable());
                    key.setUpdatable(columnAnno.updatable());
                } else {
                    key.setColumnName(fieldName);
                }
            }
            // 列注解处理
            if (columnAnno != null && idAnno == null && joinColumnAnno == null) {
                MapperColumn column = new MapperColumn();
                column.setFiledName(fieldName);
                column.setField(field);
                column.setFieldType(fieldType);
                column.setColumnName(columnAnno.name());
                column.setInsertable(columnAnno.insertable());
                column.setUpdatable(columnAnno.updatable());
                columns.add(column);
            }
            // 外键注解处理
            if (joinColumnAnno != null) {
                MapperForeignKey foreignKey = new MapperForeignKey();
                foreignKey.setForeignColumnName(joinColumnAnno.name());
                foreignKey.setField(field);
                foreignKey.setFieldType(fieldType);
                foreignKey.setInsertable(joinColumnAnno.insertable());
                foreignKey.setUpdatable(joinColumnAnno.updatable());
                if (field.getAnnotation(OneToOne.class) != null) {
                    foreignKey.setAssociationType(AssociationType.OneToOne);
                    foreignKey.setFetchType(field.getAnnotation(OneToOne.class).fetch());
                }
                if (field.getAnnotation(OneToMany.class) != null) {
                    foreignKey.setAssociationType(AssociationType.OneToMany);
                    foreignKey.setFetchType(field.getAnnotation(OneToMany.class).fetch());
                }
                if (field.getAnnotation(ManyToOne.class) != null) {
                    foreignKey.setAssociationType(AssociationType.ManyToOne);
                    foreignKey.setFetchType(field.getAnnotation(ManyToOne.class).fetch());
                }
                if (field.getAnnotation(ManyToMany.class) != null) {
                    foreignKey.setAssociationType(AssociationType.ManyToMany);
                    foreignKey.setFetchType(field.getAnnotation(ManyToMany.class).fetch());
                }
                foreignKeys.add(foreignKey);
            }

        }
        MapperEntity mapperEntity = this.scanEntityTitle();
        mapperEntity.setKey(key);
        mapperEntity.setColumns(columns);
        mapperEntity.setForeignKeys(foreignKeys);
        return mapperEntity;
    }

    // private MapperKey scanKey() {}

}
