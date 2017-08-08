package com.hhssjj.mt.processors.sql;

import org.apache.log4j.Logger;

import javax.persistence.Table;
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
            if (method.getName().startsWith("get")) {
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
        try {
            Object id = parameter.getClass()
                    .getMethod("getId")
                    .invoke(parameter);
            return id;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("通过反射获取id值失败：" + e.getMessage());
        }
    }

    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

}
