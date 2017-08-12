package com.hhssjj.mt.processors.setter;

import com.hhssjj.mt.sql.SqlCreator;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class MyBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<Object> parameterList;
    private SqlCreator sqlCreator;

    public MyBatchPreparedStatementSetter(List<Object> parameterList, SqlCreator sqlCreator) {
        this.parameterList = parameterList;
        this.sqlCreator = sqlCreator;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

    }

    @Override
    public int getBatchSize() {
        return parameterList.size();
    }
}
