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
    private String preparedSql;
    private Map<Integer, Object> valueMap;
    // 仅仅插入语句的时候使用
    private boolean isReturnId = false;

    public MyPreparedStatementCreator(SqlCreator sqlCreator) {
        this.preparedSql = sqlCreator.createPreparedSql();
        this.valueMap = sqlCreator.getValueMap();
    }

    public MyPreparedStatementCreator(String preparedSql, Map<Integer, Object> valueMap) {
        this.preparedSql = preparedSql;
        this.valueMap = valueMap;
    }

    public void setReturnId(boolean returnId) {
        isReturnId = returnId;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement;

        // 添加对插入成功返回主键的支持
        if (!isReturnId) {
            preparedStatement = connection.prepareStatement(this.preparedSql);
        } else {
            preparedStatement = connection.prepareStatement(preparedSql, Statement.RETURN_GENERATED_KEYS);
        }

        StringBuilder sb = new StringBuilder("[");
        for (Integer key : this.valueMap.keySet()) {
            Object value = this.valueMap.get(key);

            // 使用上帝类型，可能会有类型转换的错误（java.util.Date转换的时候，setObject中没有提供该类型的转换）
            if (value instanceof java.util.Date) {
                Timestamp timestamp = new Timestamp(((Date) value).getTime());
                preparedStatement.setTimestamp(key, timestamp);
            } else {
                preparedStatement.setObject(key, value);
            }
            sb.append(value + ",");
        }
        logger.info("sql parameter：" + sb.append("]").toString().replace(",]", "]"));

        return preparedStatement;
    }

}
