package com.zzz.mt.sql;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    // 存储预处理语句索引和值
    protected Map<Integer, Object> valueMap;

    protected Object[] parameters;

    protected Annotation[][] parameterAnnotations;

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

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public Map<Integer, Object> getValueMap() {
        return valueMap;
    }

}
