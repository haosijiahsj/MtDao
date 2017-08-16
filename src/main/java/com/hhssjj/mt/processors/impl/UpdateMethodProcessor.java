package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.Update;
import com.hhssjj.mt.mapping.EntityScanner;
import com.hhssjj.mt.processors.BaseMethodProcessor;
import com.hhssjj.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.sql.impl.UpdateSqlCreator;
import com.hhssjj.mt.support.Null;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.utils.Preconditions;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class UpdateMethodProcessor extends BaseMethodProcessor<Update> {
    @Override
    public Object process() {
        String userSql = methodAnnotation.value();
        String tableName = methodAnnotation.tableName();
        Class<?> entityClass = methodAnnotation.entityClass();

        Preconditions.checkArgument(!"".equals(userSql) && !"".equals(tableName),
                "You can't set parameter 'value' and 'tableName' at the same time, choose one of them");

        SqlCreator sqlCreator = new UpdateSqlCreator(userSql, tableName, entityClass);
        sqlCreator.setParameters(parameters);
        sqlCreator.setParameterAnnotations(parameterAnnotations);

        MyPreparedStatementCreator myPreparedStatementCreator;
        SqlCreateType sqlCreateType;
        // 用户自定义sql支持
        if (!"".equals(userSql)) {
            sqlCreateType = SqlCreateType.USER_DEFINE;
        } else if (!"".equals(tableName) || !Null.class.equals(entityClass)){
            sqlCreateType = SqlCreateType.FROM_MAP;
        } else {
            sqlCreateType = SqlCreateType.AUTO_CREATE;
        }
        myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, sqlCreateType);
        return jdbcTemplate.update(myPreparedStatementCreator);
    }
}
