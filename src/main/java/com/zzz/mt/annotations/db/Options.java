package com.zzz.mt.annotations.db;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public @interface Options {
    boolean useCache() default false;
    boolean flushCache() default false;
    int timeout() default 10000;
}
