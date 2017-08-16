package com.zzz.mt.annotations.db;

import java.lang.annotation.*;

/**
 * Created by 胡胜钧 on 8/2 0002.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "";
}
