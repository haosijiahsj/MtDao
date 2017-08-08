package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Update;
import com.hhssjj.mt.processors.sql.SqlCreator;
import com.hhssjj.mt.processors.sql.UpdateSqlCreator;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class UpdateMethodProcessor extends BaseMethodProcessor<Update> {
    @Override
    public Object process() throws Throwable {
        SqlCreator sqlCreator = new UpdateSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        return jdbcTemplate.update(sqlCreator.createSql());
    }
}
