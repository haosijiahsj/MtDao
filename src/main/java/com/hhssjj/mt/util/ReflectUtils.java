package com.hhssjj.mt.util;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/7 0007.
 * 反射工具类
 */
public class ReflectUtils {

    private static Logger logger = Logger.getLogger(ReflectUtils.class);

    /**
     * 判断是否是Set类型的
     * @param object
     * @return
     */
    public static boolean isList(Object object) {
        return List.class.isAssignableFrom(object.getClass());
    }

    /**
     * 判断是否是Set类型
     * @param object
     * @return
     */
    public static boolean isSet(Object object) {
        return Set.class.isAssignableFrom(object.getClass());
    }

    /**
     * 返回泛型类型的class
     * @param object
     * @return
     */
    public static Class<?> getCollectionTypeClass(Object object) {
        try {
            Method method = object.getClass().getDeclaredMethod("get", int.class);
            return method.getReturnType();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 得到对象和值的映射map
     * @param parameter
     * @return
     */
    public static Map<String, Object> getColumnAndValueMapFromObject(Object parameter) {
        Map<String, Object> map = new HashMap<>();

        Field[] fields = parameter.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            // 默认数据库列名称为字段名称
            String columnName = field.getName();
            // 通过字段名称找到get方法
            String getMethodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            // 对boolean类型特殊处理
            String filedTypeName = field.getType().getName();
            if ("boolean".equals(filedTypeName) || "java.lang.Boolean".equals(filedTypeName)) {
                if (columnName.startsWith("is")) {
                    getMethodName = columnName;
                } else if (columnName.startsWith("has") || columnName.startsWith("have")) {
                    getMethodName = "is" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                }
            }
            logger.info("get方法：" + getMethodName);
            Object value;
            try {
                value = parameter.getClass().getDeclaredMethod(getMethodName).invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("不能找到" + getMethodName + "()方法，" + e.getMessage());
            }
            // 扫描到@Column注解存在时，则设定列名为用户定义的列名
            if (column != null) columnName = column.name();

            map.put(columnName, value);
        }
        return map;
    }

}
