package com.zzz.mt.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("can't get a jdbc connection, " + e.getMessage());
        }
    }

    public static void releaseConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DataSourceUtils() {}

}
