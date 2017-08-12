package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.support.Null;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.utils.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveMethodProcessor extends BaseMethodProcessor<Save> {
    private Logger logger = Logger.getLogger(SaveMethodProcessor.class);

    @Override
    public Object process() {
        String userSql = methodAnnotation.value();
        String tableName = methodAnnotation.tableName();
        Class<?> entityClass = methodAnnotation.entityClass();
        boolean isReturnId = methodAnnotation.returnId();

        Preconditions.checkArgument(!"".equals(userSql) && !"".equals(tableName),
                "You can't set parameter 'value' and 'tableName' at the same time, choose one of them");

        SqlCreator sqlCreator = new InsertSqlCreator(userSql, tableName, entityClass);
        sqlCreator.setParameter(parameters[0]);
        sqlCreator.setParameters(parameters);
        sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.INSERT));

        // 支持用户自定义sql
        MyPreparedStatementCreator myPreparedStatementCreator;
        SqlCreateType sqlCreateType;
        if (!"".equals(userSql)) {
            sqlCreateType = SqlCreateType.USER_DEFINE;
        } else if (!"".equals(tableName) || !Null.class.equals(entityClass)) {
            sqlCreateType = SqlCreateType.FROM_MAP;
        } else{
            sqlCreateType = SqlCreateType.AUTO_CREATE;
        }
        myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, sqlCreateType);
        if (isReturnId) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            myPreparedStatementCreator.setReturnId(true);
            jdbcTemplate.update(myPreparedStatementCreator, keyHolder);
            return keyHolder.getKey().intValue();
        }

        return jdbcTemplate.update(myPreparedStatementCreator);
    }

}
