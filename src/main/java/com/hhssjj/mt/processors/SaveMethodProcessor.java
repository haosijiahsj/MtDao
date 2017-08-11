package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;
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
        boolean isReturnId = methodAnnotation.returnId();

        SqlCreator sqlCreator = new InsertSqlCreator(userSql, tableName);
        sqlCreator.setParameter(parameters[0]);
        sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.INSERT));

        if (!"".equals(userSql) && !"".equals(tableName)) {
            throw new IllegalArgumentException("You can't use them at the same time, choose one of them");
        }
        // 支持用户自定义sql
        MyPreparedStatementCreator myPreparedStatementCreator;
        if (!"".equals(userSql)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.USER_DEFINE);
        } else if (!"".equals(tableName)) {
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.FROM_MAP);
        } else{
            myPreparedStatementCreator = new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        }
        if (isReturnId) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            myPreparedStatementCreator.setReturnId(true);
            jdbcTemplate.update(myPreparedStatementCreator, keyHolder);
            return keyHolder.getKey().intValue();
        }

        return jdbcTemplate.update(myPreparedStatementCreator);
    }

}
