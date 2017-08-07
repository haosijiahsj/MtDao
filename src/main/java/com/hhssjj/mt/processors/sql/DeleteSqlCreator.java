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
    public String createSql() {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");
        try {
            Method method = parameter.getClass().getMethod("getId");
            Object id = method.invoke(parameter);
            String tableName = getTableName();
            deleteBuilder.append("`" + tableName + "` WHERE id = " + id);
            if (id == null) {
                new IllegalArgumentException("没有获取到id的值");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        logger.info("生成的删除语句：" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createPreparedSql() {
        return null;
    }
}
