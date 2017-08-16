package com.zzz.mt.sql.interceptor;

/**
 * Created by hushengjun on 2017/8/16.
 */
public class SqlInterceptorImpl implements SqlInterceptor {
    @Override
    public String intercept(String psql) {
        return psql;
    }
}
