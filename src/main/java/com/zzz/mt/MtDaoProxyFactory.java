package com.zzz.mt;

import com.zzz.mt.reflect.Reflection;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class MtDaoProxyFactory {

    private MtDaoProxyFactory() {}

    public static <T> T create(Class<T> interfaceType, JdbcTemplate jdbcTemplate) {
        MtDaoInvocationHandler invocationHandler = new MtDaoInvocationHandler(jdbcTemplate);

        return Reflection.newProxy(interfaceType, invocationHandler);
    }
}
