package com.zzz.mt.annotations.db;

import java.lang.annotation.*;
import java.util.Map;

/**
 * Created by hushengjun on 2017/9/1.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeQuery {
    String value();
    Class<?> resultType() default Map.class;
}
