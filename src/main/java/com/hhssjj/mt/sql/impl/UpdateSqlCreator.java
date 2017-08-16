package com.hhssjj.mt.sql.impl;

import com.hhssjj.mt.annotations.db.IdColumn;
import com.hhssjj.mt.reflect.Reflection;
import com.hhssjj.mt.sql.SqlCreator;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
