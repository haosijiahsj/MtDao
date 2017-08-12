package com.hhssjj.mt.annotations.db;

import com.hhssjj.mt.support.Null;

import java.lang.annotation.*;

/**
 * Created by 胡胜钧 on 8/2 0002.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Save {
    String value() default "";
    String tableName() default "";
    Class<?> entityClass() default Null.class;
    boolean returnId() default false;
}
