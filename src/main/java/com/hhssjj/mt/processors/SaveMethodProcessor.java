package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.processors.sql.InsertSqlCreator;
import com.hhssjj.mt.processors.sql.SqlCreator;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveMethodProcessor extends BaseMethodProcessor<Save> {
    private Logger logger = Logger.getLogger(SaveMethodProcessor.class);
    @Override
    public Object process() {
        final SqlCreator sqlCreator = new InsertSqlCreator();
        sqlCreator.setParameter(parameters[0]);
        // 定义一个T直接可以知道注解的类型
        boolean isReturnId = methodAnnotation.returnId();
        logger.debug("Save注解定义的值：" + isReturnId);
        if (isReturnId) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new MyPreparedStatementCreator(sqlCreator), keyHolder);
            return keyHolder.getKey().intValue();
        }
        String sql = sqlCreator.createSql();
        return jdbcTemplate.update(sql);
    }


}
