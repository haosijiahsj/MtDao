package com.hhssjj.mt.jdbc;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class JdbcTemplate {
    private Connection connection;
    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    public int saveOrUpdate(String sql, Object...parameters) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int i = 0;
            for (Object parameter : parameters) {
                preparedStatement.setObject(++i, parameter);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回id
     * @param sql
     * @param parameters
     * @return
     */
    public int saveForReturnId(String sql, Object...parameters) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            for (Object parameter : parameters) {
                preparedStatement.setObject(++i, parameter);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet query(String sql, Object...parameters) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int i = 0;
            for (Object parameter : parameters) {
                preparedStatement.setObject(++i, parameter);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
