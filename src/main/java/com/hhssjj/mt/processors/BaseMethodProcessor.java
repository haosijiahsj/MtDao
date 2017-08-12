package com.hhssjj.mt.processors;

import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public abstract class BaseMethodProcessor<T> {
    protected Method method;
    protected Object[] parameters;
    protected Annotation[][] parameterAnnotations;
    protected T methodAnnotation;
    protected JdbcTemplate jdbcTemplate;

    public abstract Object process();

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setMethodAnnotation(T methodAnnotation) {
        this.methodAnnotation = methodAnnotation;
    }

}
