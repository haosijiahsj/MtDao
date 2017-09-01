package com.zzz.mt.annotations.db;

import java.lang.annotation.*;

/**
 * Created by hushengjun on 2017/9/1.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeQuery {
    String value();
}
