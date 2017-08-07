package com.hhssjj.mt;

import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Proxy;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class MtProxyFactory {
    public static <T> T create(Class<T> clazz, JdbcTemplate jdbcTemplate) {
        MtInvocationHandler invocationHandler = new MtInvocationHandler(jdbcTemplate);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[] { clazz },
                invocationHandler);
    }
}
