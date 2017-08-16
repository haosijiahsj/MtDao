package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.BatchSave;
import com.hhssjj.mt.processors.BaseMethodProcessor;
import com.hhssjj.mt.sql.impl.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;

import java.util.List;

/**
 * Created by 胡胜钧 on 8/7 0007.
 */
public class BatchSaveMethodProcessor extends BaseMethodProcessor<BatchSave> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new InsertSqlCreator();
        List<Object> list = (List<Object>) parameters[0];
        Object object = list.get(0);

        sqlCreator.setParameters(parameters);
        jdbcTemplate.batchUpdate();
        return null;
    }
}
