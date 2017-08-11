package com.hhssjj.mt.sql;

import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.reflect.ReflectUtils;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.support.idStrategy.AutoCreateIdValue;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    private Logger logger = Logger.getLogger(SqlCreator.class);

    protected EntityMapping entityMapping;

    // 存储预处理语句索引和值
    protected Map<Integer, Object> valueMap;

    // 仅有一个参数的时候
    protected Object parameter;

    /**
     * 创建拼接的sql
     * @return
     */
    public abstract String createSql();

    /**
     * 创建预处理sql
     * @return
     */
    public abstract String createPreparedSql();

    /**
     * 从用户定义的map中来创建预处理sql
     * @return
     */
    public abstract String createPreparedSqlFromMap(String tableName);

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public void setEntityMapping(EntityMapping entityMapping) {
        this.entityMapping = entityMapping;
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

        return ReflectUtils.invoke(parameter, getIdMethod);

    }

    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

}
