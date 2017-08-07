package com.hhssjj.mt.processors.creator;

import com.hhssjj.mt.processors.sql.SqlCreator;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

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
        String preparedSql = sqlCreator.createPreparedSql();
        PreparedStatement preparedStatement = connection.prepareStatement(preparedSql, Statement.RETURN_GENERATED_KEYS);
        Map<Integer, Object> map = sqlCreator.getValueMap();
        for (Integer key : map.keySet()) {
            Object value = map.get(key);
            preparedStatement.setObject(key, value);
        }
        return preparedStatement;
    }

}
