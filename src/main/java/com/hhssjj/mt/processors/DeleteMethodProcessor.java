package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.processors.sql.DeleteSqlCreator;
import com.hhssjj.mt.processors.sql.SqlCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class DeleteMethodProcessor extends BaseMethodProcessor<Delete> {
    @Override
    public Object process() {
        String userSql = methodAnnotation.value();

        SqlCreator sqlCreator = new DeleteSqlCreator();
        sqlCreator.setParameter(parameters[0]);

        MyPreparedStatementCreator myPreparedStatementCreator;
        if ("".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator);
        } else {
            myPreparedStatementCreator = new MyPreparedStatementCreator(userSql, getParameterMap());
        }

        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
