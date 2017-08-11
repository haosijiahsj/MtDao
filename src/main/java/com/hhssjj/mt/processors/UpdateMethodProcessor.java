package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Update;
import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.sql.UpdateSqlCreator;
import com.hhssjj.mt.support.SqlType;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class UpdateMethodProcessor extends BaseMethodProcessor<Update> {
    @Override
    public Object process() {
        SqlCreator sqlCreator = new UpdateSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.UPDATE));
        String userSql = methodAnnotation.value();
        String tableName = methodAnnotation.tableName();

        if (!"".equals(userSql) && !"".equals(tableName)) {
            throw new IllegalArgumentException("You can't use them at the same time, choose one of them");
        }
        MyPreparedStatementCreator myPreparedStatementCreator;
        // 用户自定义sql支持
        if (!"".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(userSql, super.getParameterMap());
        } else if (!"".equals(tableName)){
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator.createPreparedSqlFromMap(tableName),
                    sqlCreator.getValueMap());
        } else {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator);
        }
        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
