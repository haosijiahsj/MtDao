package com.zzz.mt.sql.impl;

import com.zzz.mt.sql.SqlCreator;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class UpdateSqlCreator extends SqlCreator {

    private Logger logger = Logger.getLogger(UpdateSqlCreator.class);
    private String sql;
    private String tableName;
    private Class<?> entityClass;

    public UpdateSqlCreator() {}

    public UpdateSqlCreator(String sql, String tableName, Class<?> clazz) {
        this.sql = sql;
        this.tableName = tableName;
        this.entityClass = clazz;
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
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");

        return sql;
    }

    @Override
    public String createPreparedSqlFromMap() {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");

        return sql;
    }
}
