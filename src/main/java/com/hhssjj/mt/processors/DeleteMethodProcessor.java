package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.DeleteSqlCreator;
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
        sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.DELETE));

        MyPreparedStatementCreator myPreparedStatementCreator;
        if (!"".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.USER_DEFINE);
        } else {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        }

        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
