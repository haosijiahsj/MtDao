package com.hhssjj.mt.jdbc.datasource;

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
            return null;
        }
    }

}
