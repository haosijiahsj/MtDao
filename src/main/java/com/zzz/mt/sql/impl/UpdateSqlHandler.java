package com.zzz.mt.sql.impl;

import com.zzz.mt.sql.SqlHandler;

/**
 * Created by 胡胜钧 on 9/2 0002.
 */
public class UpdateSqlHandler extends SqlHandler {

    private boolean returnKey;

    public UpdateSqlHandler(boolean returnKey) {
        this.returnKey = returnKey;
    }

    @Override
    public Object sqlHandler() {
        if (returnKey) {
            return null;
        }
        return jdbcOperations.update(psql, parameters);
    }
}
