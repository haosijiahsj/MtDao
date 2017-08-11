package com.hhssjj.mt.sql;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class DeleteSqlCreator extends SqlCreator {

    protected Logger logger = Logger.getLogger(DeleteSqlCreator.class);

    @Override
    public String createSql() {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");

        Object id = entityMapping.getIdValue();
        String tableName = entityMapping.getTableName();
        deleteBuilder.append("`")
                .append(tableName)
                .append("` WHERE `")
                .append(entityMapping.getIdColumnName())
                .append("` = ")
                .append(id);

        logger.info("sql statement：" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createUserSql(String sql) {
        logger.info("sql statement：" + sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");

        String tableName = entityMapping.getTableName();
        deleteBuilder.append("`")
                .append(tableName)
                .append("` WHERE `")
                .append(entityMapping.getIdColumnName())
                .append("` = ?");

        // 必须重新初始化
        valueMap = new HashMap<>();
        valueMap.put(1, entityMapping.getIdValue());
        logger.info("sql statement:" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createPreparedSqlFromMap(String tableName) {
        return null;
    }
}
