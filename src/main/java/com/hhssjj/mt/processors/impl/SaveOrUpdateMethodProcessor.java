package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.mapping.EntityScanner;
import com.hhssjj.mt.processors.BaseMethodProcessor;
import com.hhssjj.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.impl.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.sql.impl.UpdateSqlCreator;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveOrUpdateMethodProcessor extends BaseMethodProcessor<SaveOrUpdate> {

    @Override
    public Object process() {
        EntityScanner entityScanner = new EntityScanner(parameters[0]);
        Object idValue = entityScanner.getIdValue();
        SqlCreator sqlCreator;
        if (idValue == null) {
            sqlCreator = new InsertSqlCreator();
            sqlCreator.setParameters(parameters);
        } else {
            sqlCreator = new UpdateSqlCreator();
            sqlCreator.setParameters(parameters);
        }

        MyPreparedStatementCreator myPreparedStatementCreator =
                new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        return jdbcTemplate.update(myPreparedStatementCreator);
    }

}
