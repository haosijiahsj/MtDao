package com.hhssjj.mt.mapping;

import com.hhssjj.mt.reflect.Reflection;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.support.id.AutoCreateIdValue;
import com.hhssjj.mt.utils.Preconditions;

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
    private SqlType sqlType;

    public EntityScanner(Object object) {
        this.object = object;
    }

    public EntityScanner(Object object, SqlType sqlType) {
        this.object = object;
        this.sqlType = sqlType;
    }

    /**
     * 扫描实体类，获取其中的所有属性
     * @return
     */
    public PersistentEntity scanEntityByField() {
        PersistentEntity persistentEntity = new PersistentEntity();
        List<PersistentProperty> propertyList = new ArrayList<>();

        Class<?> clazz = object.getClass();
        String entityName = clazz.getName();
        String tableName = entityName;
        Annotation[] entityAnnos = clazz.getAnnotations();
        Entity entity = clazz.getAnnotation(Entity.class);
        Preconditions.checkNotNull(entity,
                object + " is not a database entity, maybe you should add annotation '@Entity'");

        Table table = clazz.getAnnotation(Table.class);
        if (table != null && !"".equals(table.name())) tableName = table.name();

        Field[] fields = Reflection.getDeclaredFields(object);
        for (Field field : fields) {
            PersistentProperty persistentProperty = new PersistentProperty();
            Annotation[] annotations = field.getAnnotations();

            Column columnAnno = field.getAnnotation(Column.class);

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            String columnName = fieldName;
            if (columnAnno != null) columnName = columnAnno.name();

            Object value = Reflection.get(object, field);

            persistentProperty.setFieldName(fieldName)
                    .setAnnotations(annotations)
                    .setColumnName(columnName)
                    .setFieldType(fieldType)
                    .setValue(value);

            propertyList.add(persistentProperty);
        }

        persistentEntity.setEntityName(entityName)
                .setTableName(tableName)
                .setEntityAnnotation(entityAnnos)
                .setPropertyList(propertyList);

        return persistentEntity;
    }

    public List<PersistentProperty> scanEntityByMethod() {
        List<PersistentProperty> propertyList = new ArrayList<>();

        Method[] methods = Reflection.getDeclaredMethods(object);
        for (Method method : methods) {
            PersistentProperty persistentProperty = new PersistentProperty();
            Annotation[] annotations = method.getAnnotations();

            Column columnAnno = method.getAnnotation(Column.class);
            // 没有Column注解直接跳过
            if (columnAnno == null) continue;

            String columnName = columnAnno.name();
            String methodName = method.getName();
            Class<?> fieldType = method.getReturnType();

            Object value = Reflection.invoke(object, methodName);

            persistentProperty.setAnnotations(annotations)
                    .setFieldType(fieldType)
                    .setColumnName(columnName)
                    .setValue(value);

            propertyList.add(persistentProperty);
        }

        return propertyList;
    }

    /**
     * 生成类名与表名称映射
     * @return
     */
    public Map<String, String> getEntityAndTableMap() {
        Map<String, String> entityAndTableMap = new HashMap<>();

        String entityName = object.getClass().getName();
        String tableName = entityName;
        Entity entity = object.getClass().getAnnotation(Entity.class);

        Preconditions.checkNotNull(entity,
                object + " is not a database entity, maybe you should add annotation '@Entity'");

        Table table = object.getClass().getAnnotation(Table.class);
        if (table != null && !"".equals(table.name())) tableName = table.name();

        entityAndTableMap.put(entityName, tableName);

        return entityAndTableMap;
    }

    public String getTableName(Class<?> clazz) {
        String tableName = clazz.getName();
        Entity entity = clazz.getAnnotation(Entity.class);

        Preconditions.checkNotNull(entity,
                object + " is not a database entity, maybe you should add annotation '@Entity'");

        Table table = clazz.getAnnotation(Table.class);
        if (table != null && !"".equals(table.name())) tableName = table.name();

        return tableName;
    }
    /**
     * 扫描实体类得到表名
     * @return
     */
    public String getTableName() {
        return getTableName(object.getClass());
    }

    /**
     * 获取id列名，用户定义的不一定是id
     * @return
     */
    public String getIdColumnName() {
        Field[] fields = Reflection.getDeclaredFields(object);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                if (column != null && !"".equals(column.name())) {
                    return column.name();
                }
                return field.getName();
            }
        }
        // 必须定义@Id注解，否则报错
        throw new IllegalArgumentException(object + " must define '@Id' annotation, but you not!");
    }

    /**
     * 生成字段名与数据库列名的映射
     * @return
     */
    public Map<String, String> getFieldAndColumnMap() {
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

        Field[] fields = Reflection.getDeclaredFields(object);
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

            Object value = Reflection.invoke(object, getMethodName);

            // 当sql语句为插入类型的时候去生成主键
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

                Preconditions.checkArgument(nullAble,
                        "'" + columnName + "' can't accept null value");

                columnName = columnAnnotation.name();
            }

            map.put(columnName, value);
        }

        return map;
    }

    /**
     * 获取id列与值的映射
     * @return
     */
    public Map<String, Object> getIdColumnAndValueMap() {
        Map<String, Object> map = new HashMap<>();
        String getIdMethod = "getId";
        Field[] fields = Reflection.getDeclaredFields(object);
        String idColumnName = "id";
        int i = 0;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                idColumnName = field.getName();
                if (column != null && !"".equals(column.name())) {
                    idColumnName = column.name();
                }
                getIdMethod = "get" + idColumnName.substring(0, 1).toUpperCase() + idColumnName.substring(1);
                i++;
            }
        }
        // 必须定义@Id注解，否则报错
        if (i == 0) throw new IllegalArgumentException(object + " must define '@Id' annotation, but you not!");

        Object value = Reflection.invoke(object, getIdMethod);
        map.put(idColumnName, value);

        return map;
    }

    /**
     * 获取id的值
     * @return
     */
    public Object getIdValue() {
        Field[] fields = Reflection.getDeclaredFields(object);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                String idColumnName = field.getName();
                if (column != null && !"".equals(column.name())) {
                    idColumnName = column.name();
                }
                String getIdMethod = "get" + idColumnName.substring(0, 1).toUpperCase() + idColumnName.substring(1);
                return Reflection.invoke(object, getIdMethod);
            }
        }
        throw new IllegalArgumentException(object + " must define '@Id' annotation, but you not!");
    }

}
