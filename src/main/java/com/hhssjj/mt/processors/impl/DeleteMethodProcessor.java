package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.mapping.EntityScanner;
import com.hhssjj.mt.processors.BaseMethodProcessor;
import com.hhssjj.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.impl.DeleteSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class DeleteMethodProcessor extends BaseMethodProcessor<Delete> {
    @Override
    public Object process() {
        String userSql = methodAnnotation.value();

        SqlCreator sqlCreator = new DeleteSqlCreator(userSql);
        sqlCreator.setParameter(parameters[0]);
        sqlCreator.setParameters(parameters);
        sqlCreator.setEntityScanner(new EntityScanner(parameters[0], SqlType.DELETE));

        MyPreparedStatementCreator myPreparedStatementCreator;
        if (!"".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.USER_DEFINE);
        } else {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        }

        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
