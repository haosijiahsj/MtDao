package com.zzz.mt.sql;

import com.zzz.mt.jdbc.JdbcOperations;

import java.util.Map;

/**
 * Created by 胡胜钧 on 9/2 0002.
 */
public abstract class SqlHandler {

    protected Object[] parameters;

    protected JdbcOperations jdbcOperations;

    protected String psql;

    public abstract Object sqlHandler();

    public void setJdbcOperations(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public void setPsql(String psql) {
        this.psql = psql;
    }

}
