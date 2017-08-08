package com.hhssjj.mt.processors.creator;

import com.hhssjj.mt.processors.sql.SqlCreator;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by 胡胜钧 on 8/7 0007.
 */
public class MyPreparedStatementCreator implements PreparedStatementCreator {

    private Logger logger = Logger.getLogger(MyPreparedStatementCreator.class);
    private SqlCreator sqlCreator;

    public MyPreparedStatementCreator(SqlCreator sqlCreator) {
        this.sqlCreator = sqlCreator;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        String preparedSql = null;
        try {
            preparedSql = sqlCreator.createPreparedSql();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(preparedSql,
                Statement.RETURN_GENERATED_KEYS);
        Map<Integer, Object> map = sqlCreator.getValueMap();
        for (Integer key : map.keySet()) {
            Object value = map.get(key);

            // 使用上帝类型，可能会有类型转换的错误（java.util.Date转换的时候，setObject中没有提供该类型的转换）
            if (value instanceof java.util.Date) {
                Timestamp timestamp = new Timestamp(((Date) value).getTime());
                preparedStatement.setTimestamp(key, timestamp);
            } else {
                preparedStatement.setObject(key, value);
            }
        }

        return preparedStatement;
    }

}
