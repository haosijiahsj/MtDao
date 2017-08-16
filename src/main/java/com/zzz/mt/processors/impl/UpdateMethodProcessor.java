package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.Update;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.sql.impl.UpdateSqlCreator;
import com.zzz.mt.support.Null;
import com.zzz.mt.support.SqlCreateType;
import com.zzz.mt.utils.Preconditions;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class UpdateMethodProcessor extends BaseMethodProcessor<Update> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new UpdateSqlCreator();
        sqlCreator.setParameters(parameters);

        MyPreparedStatementCreator myPreparedStatementCreator
                = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
