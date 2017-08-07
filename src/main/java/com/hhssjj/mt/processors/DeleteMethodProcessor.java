package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.processors.sql.DeleteSqlCreator;
import com.hhssjj.mt.processors.sql.SqlCreator;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class DeleteMethodProcessor extends BaseMethodProcessor<Delete> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new DeleteSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        return jdbcTemplate.update(sqlCreator.createSql());
    }
}
