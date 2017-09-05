package com.zzz.mt.sql;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public abstract class SqlCreator {

    protected Object[] parameters;

    protected Annotation[][] parameterAnnotations;

    protected String psql;

    protected Object[] values;

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public String getPsql() {
        return this.psql;
    }

    public Object[] getValues() {
        return this.values;
    }

}
