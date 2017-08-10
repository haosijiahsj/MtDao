package com.hhssjj.mt.processors.sql;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    private Logger logger = Logger.getLogger(SqlCreator.class);

    // 存储预处理语句索引和值
    protected Map<Integer, Object> valueMap;

    // 仅有一个参数的时候
    protected Object parameter;

    public abstract String createSql();

    public abstract String createPreparedSql();

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    /**
     * 获取实体类注解
     * @return
     */
    protected Annotation[] getAnnotations() {
        return parameter.getClass().getAnnotations();
    }

    /**
     * 获取实体类中的@Table注解
     * @return
     */
    protected Table getTableAnnotation() {
        return parameter.getClass().getAnnotation(Table.class);
    }

    /**
     * 获取@Table注解中的表名，若该注解不存在，则使用类名作为表名
     * @return
     */
    protected String getTableName() {
        Table tableAnnotation = getTableAnnotation();
        String tableName;
        if (tableAnnotation == null) {
            tableName = parameter.getClass().getName();
        } else {
            tableName = tableAnnotation.name();
        }
        return tableName;
    }

    /**
     * 获取实体类中的所有方法
     * @return
     */
    protected Method[] getMethods() {
        return parameter.getClass().getMethods();
    }

    /**
     * 获取实体类中的get方法
     * @return
     */
    protected Method[] getGetMethods() {
        List<Method> methodList = new ArrayList<>();
        for (Method method : getMethods()) {
            if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                methodList.add(method);
            }
        }
        Method[] getMethods = new Method[methodList.size()];
        return methodList.toArray(getMethods);
    }

    protected Field[] getFields() {
        return parameter.getClass().getDeclaredFields();
    }

    protected Object getIdValue() {
        String getIdMethod = "getId";
        for (Field field : getFields()) {
            String fieldName = field.getName();
            if (field.getAnnotation(Id.class) != null) {
                getIdMethod = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            }
        }
        try {
            return parameter.getClass().getMethod(getIdMethod).invoke(parameter);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("通过反射获取id值失败：" + e.getMessage());
        }
    }

    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

    /**
     * 获取字段与对应值的映射
     * @return
     */
    protected Map<String, Object> getColumnAndValueMapFromObject() {
        Map<String, Object> map = new LinkedHashMap<>();

        Field[] fields = parameter.getClass().getDeclaredFields();
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);
            // 默认数据库列名称为字段名称
            String columnName = field.getName();
            // 通过字段名称找到get方法
            String getMethodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            // 对boolean类型特殊处理
            String filedTypeName = field.getType().getName();
            if ("boolean".equals(filedTypeName) || "java.lang.Boolean".equals(filedTypeName)) {
                if (columnName.startsWith("is")) {
                    getMethodName = columnName;
                } else if (columnName.startsWith("has") || columnName.startsWith("have")) {
                    getMethodName = "is" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                }
            }
            logger.info("get方法：" + getMethodName);
            Object value;
            try {
                value = parameter.getClass().getDeclaredMethod(getMethodName).invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("can't find '" + getMethodName + "()' method," + e.getMessage());
            }
            if (idAnnotation != null) {
                GeneratedValue generatedValueAnnotation = field.getAnnotation(GeneratedValue.class);
                if (generatedValueAnnotation == null) continue;
                else {
                    GenerationType generationType = generatedValueAnnotation.strategy();
                    if (GenerationType.IDENTITY.equals(generationType)) continue;
                    else if (GenerationType.AUTO.equals(generationType)) {
                        // 自动判断数据库类型，然后使用不同的主键生成策略
                        String driverName = System.getProperty("jdbc.driver");
                        logger.info("数据库驱动：" +driverName);
                        throw new IllegalStateException("sorry! at present MtDao can't support this id generate strategy");
                    } else if (GenerationType.SEQUENCE.equals(generationType)) {
                        // Oracle数据库的主键生成策略
                        throw new IllegalStateException("sorry! at present MtDao can't support this id generate strategy");
                    } else {
                        // 这个需要维护一张表
                        throw new IllegalStateException("sorry! at present MtDao can't support this id generate strategy");
                    }
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
