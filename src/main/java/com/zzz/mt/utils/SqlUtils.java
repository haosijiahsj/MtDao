package com.zzz.mt.utils;

import com.zzz.mt.support.DataBaseType;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class SqlUtils {

    /**
     * 判断是否是传入的数据库类型
     * @param dataBaseType
     * @return
     */
    public static boolean isCurDatabaseType(DataBaseType dataBaseType) {
        Enumeration<Driver> driverNames = DriverManager.getDrivers();
        while (driverNames.hasMoreElements()) {
            Driver driver = driverNames.nextElement();
            String driverName = driver.getClass().getName();
            if (driverName.toLowerCase().contains(dataBaseType.name().toLowerCase())) return true;
        }
        return false;
    }

    private SqlUtils() {}

}
