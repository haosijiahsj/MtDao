package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.BatchSave;
import com.hhssjj.mt.sql.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;

/**
 * Created by 胡胜钧 on 8/7 0007.
 */
public class BatchSaveMethodProcessor extends BaseMethodProcessor<BatchSave> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new InsertSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        jdbcTemplate.batchUpdate();
        return null;
    }
}
