package com.hhssjj.mt.mapping;

import com.hhssjj.mt.reflect.ReflectUtils;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.support.idStrategy.AutoCreateIdValue;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hushengjun on 2017/8/11.
 */
public class EntityMapping {
    private Object object;
    private SqlType sqlType;

    private EntityMapping(Object object) {
        this.object = object;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    /**
     * 生成类名与表名称映射
     * @return
     */
    public Map<String, String> generateEntityAndTableMap() {
        Map<String, String> entityAndTableMap = new HashMap<>();

        String entityName = object.getClass().getName();
        String tableName = entityName;
        Entity entity = object.getClass().getAnnotation(Entity.class);

        if (entity == null)
            throw new IllegalStateException(object + " is not a database entity, maybe you should add annotation '@Entity'");
        else {
            Table table = object.getClass().getAnnotation(Table.class);
            if (table != null && !"".equals(table.name())) tableName = table.name();
        }

        entityAndTableMap.put(entityName, tableName);

        return entityAndTableMap;
    }

    /**
     * 生成字段名与数据库列名的映射
     * @return
     */
    public Map<String, String> generateFieldAndColumnMap() {
        Map<String, String> fieldAndColumnMap = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            Transient transientAnno = field.getAnnotation(Transient.class);

            String fieldName = field.getName();
            String columnName = fieldName;
            if (id != null) {
                if (column != null && !"".equals(column.name())) columnName = column.name();
            }
            if (column != null && !"".equals(column.name())) {
                if (transientAnno == null) columnName = column.name();
                else continue;
            }

            fieldAndColumnMap.put(fieldName, columnName);
        }

        return fieldAndColumnMap;
    }

    /**
     * 获取字段与对应值的映射
     * @return
     */
    public Map<String, Object> getColumnAndValueMapFromObject() {
        Map<String, Object> map = new LinkedHashMap<>();

        Field[] fields = ReflectUtils.getDeclaredFields(object);
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName = field.getName();
            String getMethodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            String filedTypeName = field.getType().getName();

            if ("boolean".equals(filedTypeName) || "java.lang.Boolean".equals(filedTypeName)) {
                if (columnName.startsWith("is")) {
                    getMethodName = columnName;
                } else if (columnName.startsWith("has") || columnName.startsWith("have")) {
                    getMethodName = "is" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                }
            }

            Object value = ReflectUtils.invoke(object, getMethodName);

            // 当sql语句为插入类型的时候去找主键
            if (SqlType.INSERT.equals(sqlType)) {
                // 当@Id注解存在时，获取主键生成策略，获取id的值，为null的表示数据库自动生成
                if (idAnnotation != null) {
                    GeneratedValue generatedValueAnnotation = field.getAnnotation(GeneratedValue.class);
                    Object idValue = new AutoCreateIdValue(generatedValueAnnotation).createId();
                    if (idValue != null) value = idValue;
                    else continue;
                }
            }

            // 扫描到@Column注解存在时，则设定列名为用户定义的列名
            if (columnAnnotation != null) {
                boolean nullAble = columnAnnotation.nullable();
                if (value == null) {
                    if (!nullAble) throw new IllegalArgumentException("'" + columnName + "' can't accept null value");
                }
                columnName = columnAnnotation.name();
            }

            map.put(columnName, value);
        }

        return map;
    }

}
