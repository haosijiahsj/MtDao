package com.hhssjj.mt.sql.impl;

import com.hhssjj.mt.sql.SqlCreator;
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

        return "";
    }

    @Override
    public String createPreparedSqlFromMap() {
        return null;
    }
}
