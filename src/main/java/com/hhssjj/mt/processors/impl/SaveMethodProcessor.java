package com.hhssjj.mt.processors.impl;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.processors.SingleParamMethodProcessor;
import com.hhssjj.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.impl.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.support.SqlCreateType;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveMethodProcessor extends SingleParamMethodProcessor<Save> {

    @Override
    public Object process() {
        boolean isReturnId = methodAnnotation.returnId();

        SqlCreator sqlCreator = new InsertSqlCreator();
        sqlCreator.setParameters(parameters);

        MyPreparedStatementCreator myPreparedStatementCreator;
        SqlCreateType sqlCreateType = SqlCreateType.AUTO_CREATE;

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
