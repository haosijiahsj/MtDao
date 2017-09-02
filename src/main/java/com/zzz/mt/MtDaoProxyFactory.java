package com.zzz.mt;

import com.zzz.mt.jdbc.JdbcOperations;
import com.zzz.mt.reflect.Reflection;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class MtDaoProxyFactory {

    private MtDaoProxyFactory() {}

    public static <T> T create(Class<T> interfaceType, JdbcOperations jdbcOperations) {
        MtDaoInvocationHandler invocationHandler = new MtDaoInvocationHandler(jdbcOperations);

        return Reflection.newProxy(interfaceType, invocationHandler);
    }
}
