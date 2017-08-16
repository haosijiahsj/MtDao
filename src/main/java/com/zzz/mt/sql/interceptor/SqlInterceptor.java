package com.zzz.mt.sql.interceptor;

/**
 * Created by hushengjun on 2017/8/16.
 */
public interface SqlInterceptor {

    String intercept(String psql);

}
