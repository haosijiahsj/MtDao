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

    protected Object[] parameters;

    /**
     * 创建拼接的sql
     * @return
     */
    public abstract String createSql();

    /**
     * 直接返回用户传入的sql
     * @return
     */
    public abstract String createUserSql();

    /**
     * 创建预处理sql
     * @return
     */
    public abstract String createPreparedSql();

    /**
     * 从用户定义的map中来创建预处理sql
     * @return
     */
    public abstract String createPreparedSqlFromMap();

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
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


    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

}
