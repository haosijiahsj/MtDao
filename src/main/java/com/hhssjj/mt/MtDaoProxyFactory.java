package com.hhssjj.mt;

import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Proxy;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class MtDaoProxyFactory {

    private MtDaoProxyFactory() {}

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz, JdbcTemplate jdbcTemplate) {
        MtDaoInvocationHandler invocationHandler = new MtDaoInvocationHandler(jdbcTemplate);

        // 不是接口不能被代理
        if (!clazz.isInterface()) throw new IllegalArgumentException(clazz + "不是接口，不能被代理！");

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[] { clazz },
                invocationHandler);
    }
}
