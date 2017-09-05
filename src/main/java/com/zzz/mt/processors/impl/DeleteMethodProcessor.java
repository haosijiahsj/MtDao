package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.Delete;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.zzz.mt.sql.impl.DeleteSqlCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.support.SqlCreateType;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class DeleteMethodProcessor extends BaseMethodProcessor<Delete> {
    @Override
    public Object process() {
        return null;
    }
}
