package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.Update;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.sql.impl.UpdateSqlCreator;
import com.zzz.mt.support.Null;
import com.zzz.mt.support.SqlCreateType;
import com.zzz.mt.utils.Preconditions;

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
