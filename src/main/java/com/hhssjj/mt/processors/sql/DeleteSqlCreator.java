package com.hhssjj.mt.processors.sql;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class DeleteSqlCreator extends SqlCreator {

    protected Logger logger = Logger.getLogger(DeleteSqlCreator.class);

    @Override
    public String createSql() throws Throwable {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");

        Method method = parameter.getClass().getMethod("getId");
        Object id = method.invoke(parameter);
        String tableName = getTableName();
        deleteBuilder.append("`" + tableName + "` WHERE `id` = " + id);

        logger.info("生成的删除语句：" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createPreparedSql() {
        return null;
    }
}
