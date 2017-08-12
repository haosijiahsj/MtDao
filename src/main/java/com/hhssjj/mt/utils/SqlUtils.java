package com.hhssjj.mt.utils;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class SqlUtils {

    /**
     * 判断是否是传入的数据库类型
     * @param name
     * @return
     */
    public static boolean isCurDatabaseType(String name) {
        Enumeration<Driver> driverNames = DriverManager.getDrivers();
        while (driverNames.hasMoreElements()) {
            Driver driver = driverNames.nextElement();
            String driverName = driver.getClass().getName();
            if (driverName.toLowerCase().contains(name)) return true;
        }
        return false;
    }

    private SqlUtils() {}

}
