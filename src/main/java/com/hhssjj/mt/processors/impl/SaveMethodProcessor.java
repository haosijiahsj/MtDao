package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.mapping.EntityScanner;
import com.hhssjj.mt.processors.SingleParameterMethodProcessor;
import com.hhssjj.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.impl.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.support.Null;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;
import com.hhssjj.mt.utils.Preconditions;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveMethodProcessor extends SingleParameterMethodProcessor<Save> {

    @Override
    public Object process() {
        Class<?> entityClass = methodAnnotation.entityClass();
        boolean isReturnId = methodAnnotation.returnId();

        SqlCreator sqlCreator = new InsertSqlCreator();
        sqlCreator.setParameters(parameters);
        sqlCreator.setEntityScanner(new EntityScanner(parameters[0], SqlType.INSERT));

        // 支持用户自定义sql
        MyPreparedStatementCreator myPreparedStatementCreator;
        SqlCreateType sqlCreateType;
        if (!Null.class.equals(entityClass)) {
            sqlCreateType = SqlCreateType.FROM_MAP;
        } else {
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
