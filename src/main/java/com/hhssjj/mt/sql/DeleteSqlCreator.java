package com.hhssjj.mt.sql;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class DeleteSqlCreator extends SqlCreator {

    private Logger logger = Logger.getLogger(DeleteSqlCreator.class);

    private String sql;

    public DeleteSqlCreator() {}

    public DeleteSqlCreator(String sql) {
        this.sql = sql;
    }

    @Override
    public String createSql() {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");

        Object id = entityScanner.getIdValue();
        String tableName = entityScanner.getTableName();
        deleteBuilder.append("`")
                .append(tableName)
                .append("` WHERE `")
                .append(entityScanner.getIdColumnName())
                .append("` = ")
                .append(id);

        logger.info("sql statement：" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createUserSql() {
        valueMap = new HashMap<>();
        int i = 0;
        for (Object value : parameters) {
            valueMap.put(++i, value);
        }
        logger.info("sql statement: " + this.sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        StringBuilder deleteBuilder = new StringBuilder("DELETE FROM ");

        String tableName = entityScanner.getTableName();
        deleteBuilder.append("`")
                .append(tableName)
                .append("` WHERE `")
                .append(entityScanner.getIdColumnName())
                .append("` = ?");

        // 必须重新初始化
        valueMap = new HashMap<>();
        valueMap.put(1, entityScanner.getIdValue());
        logger.info("sql statement:" + deleteBuilder.toString());
        return deleteBuilder.toString();
    }

    @Override
    public String createPreparedSqlFromMap() {
        return null;
    }
}
