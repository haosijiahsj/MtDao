package com.zzz.mt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hushengjun on 2017/8/17.
 */
public class JdbcBaseTemplate {

    public static int update(Connection con, String sql) throws SQLException {
        return con.createStatement().executeUpdate(sql);
    }
    /**
     * 更新方法
     * @param con
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static int update(Connection con, String sql, Object...args) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        int i = 0;
        for (Object arg : args) {
            preparedStatement.setObject(++i, arg);
        }
        return preparedStatement.executeUpdate();
    }

    /**
     * 直接传入一个预处理，上层方法写回调
     * @param pstat
     * @return
     * @throws SQLException
     */
    public static int update(PreparedStatement pstat) throws SQLException {
        return pstat.executeUpdate();
    }

    /**
     * 查询方法
     * @param con
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static ResultSet query(Connection con, String sql, Object...args) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        int i = 0;
        for (Object arg : args) {
            preparedStatement.setObject(++i, arg);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * 传入预处理查询
     * @param pstat
     * @return
     * @throws SQLException
     */
    public static ResultSet query(PreparedStatement pstat) throws SQLException {
        return pstat.executeQuery();
    }

}
