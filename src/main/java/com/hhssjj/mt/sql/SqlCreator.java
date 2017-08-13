package com.hhssjj.mt.sql;

import com.hhssjj.mt.mapping.EntityScanner;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    private Logger logger = Logger.getLogger(SqlCreator.class);

    protected EntityScanner entityScanner;

    // 存储预处理语句索引和值
    protected Map<Integer, Object> valueMap;

    // 仅有一个参数的时候
    protected Object parameter;

    protected Object[] parameters;

    protected Annotation[][] parameterAnnotations;

    /**
     * 创建拼接的sql，有注入风险，不使用了
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

    public void setEntityScanner(EntityScanner entityScanner) {
        this.entityScanner = entityScanner;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

}
