package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.NativeUpdate;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.sql.SqlHandler;
import com.zzz.mt.sql.impl.UpdateSqlHandler;

/**
 * Created by hushengjun on 2017/8/9.
 */
public class NativeUpdateMethodProcessor extends BaseMethodProcessor<NativeUpdate> {
    @Override
    public Object process() {
        String psql = methodAnnotation.value();
        boolean returnKey = methodAnnotation.returnKey();

        SqlHandler sqlHandler = new UpdateSqlHandler(returnKey);
        sqlHandler.setJdbcOperations(jdbcOperations);
        sqlHandler.setPsql(psql);

        return sqlHandler.sqlHandler();
    }
}
