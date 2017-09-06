package com.zzz.mt.sql;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    /**
     * 方法中的参数
     */
    protected Object[] parameters;

    /**
     * 方法中参数的注解
     */
    protected Annotation[][] parameterAnnotations;

    /**
     * 这是sql中的参数
     */
    protected Object[] values;

    public abstract String generatePsql();

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public Object[] getValues() {
        return this.values;
    }

}
