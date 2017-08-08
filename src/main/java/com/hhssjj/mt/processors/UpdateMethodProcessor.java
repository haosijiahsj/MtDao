package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Update;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.processors.sql.SqlCreator;
import com.hhssjj.mt.processors.sql.UpdateSqlCreator;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class UpdateMethodProcessor extends BaseMethodProcessor<Update> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new UpdateSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        String userSql = methodAnnotation.value();
        MyPreparedStatementCreator myPreparedStatementCreator;
        // 用户自定义sql支持
        if ("".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator);
        } else {
            myPreparedStatementCreator = new MyPreparedStatementCreator(userSql, getParameterMap());
        }
        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
