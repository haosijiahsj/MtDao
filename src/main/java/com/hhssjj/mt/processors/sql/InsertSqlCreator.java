package com.hhssjj.mt.processors.sql;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class InsertSqlCreator extends SqlCreator {

    private Logger logger = Logger.getLogger(InsertSqlCreator.class);

    @Override
    public String createSql() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        StringBuilder columnBuilder = new StringBuilder("(");
        StringBuilder valueBuilder = new StringBuilder("(");
        Field[] fields = getFields();
        String tableName = getTableName();

        // 拼装表名
        sqlBuilder.append("`" + tableName + "`");

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            String fieldName = field.getName();
            if ("id".equals(fieldName)) {
                continue;
            }

            // 通过字段名称找到get方法
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            logger.info("get方法：" + getMethodName);

            Object value;
            try {
                value = parameter.getClass()
                        .getDeclaredMethod(getMethodName)
                        .invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("不能找到" + getMethodName + "()方法，" + e.getMessage());
            }
            // 如果获取到的值为null，则不拼接该字段到sql语句中
            if (value == null) {
                continue;
            }
            // 设置数据库列字段
            if (column == null) {
                columnBuilder.append("`" + fieldName + "`,");
            } else {
                columnBuilder.append("`" + column.name() + "`,");
            }
            // 判断该字段的类型，拼装不同的值
            if (String.class.equals(value.getClass()) || Timestamp.class.equals(value.getClass()) ||
                    java.sql.Date.class.equals(value.getClass())) {
                valueBuilder.append("'" + value + "',");
            } else if (boolean.class.equals(value.getClass()) || Boolean.class.equals(value.getClass())) {
                int tempValue = (boolean) value ? 1 :0;
                valueBuilder.append(tempValue + ",");
            } else if (Date.class.equals(value.getClass())) {
                // 仅有java.util.Date需要转换成时间戳插入数据库，但java.sql.Date插入数据库中仅保留日期
                Timestamp tempValue = new Timestamp(((Date) value).getTime());
                valueBuilder.append("'" + tempValue + "',");
            } else {
                valueBuilder.append(value + ",");
            }

        }
        columnBuilder.append(")");
        valueBuilder.append(")");

        sqlBuilder.append(columnBuilder.toString().replace(",)", ")"));
        sqlBuilder.append(" VALUES ");
        sqlBuilder.append(valueBuilder.toString().replace(",)", ")"));
        String sql = sqlBuilder.toString();
        logger.info("生成的插入语句：" + sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        valueMap = new HashMap<>();

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        StringBuilder columnBuilder = new StringBuilder("(");
        StringBuilder valueBuilder = new StringBuilder("(");
        Field[] fields = getFields();
        String tableName = getTableName();

        // 拼装表名
        sqlBuilder.append("`" + tableName + "`");
        int i = 0;
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            String fieldName = field.getName();
            if ("id".equals(fieldName)) {
                continue;
            }

            // 通过字段名称找到get方法
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            logger.info("get方法：" + getMethodName);

            Object value;
            try {
                value = parameter.getClass()
                        .getDeclaredMethod(getMethodName)
                        .invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("不能找到" + getMethodName + "()方法，" + e.getMessage());
            }

            // 如果获取到的值为null，则不拼接该字段到sql语句中
            if (value == null) {
                continue;
            }

            // 设置数据库列字段
            if (column == null) {
                columnBuilder.append("`" + fieldName + "`,");
            } else {
                columnBuilder.append("`" + column.name() + "`,");
            }

            // 预处理语句直接拼装问号
            valueBuilder.append("?,");
            valueMap.put(++i, value);
        }

        columnBuilder.append(")");
        valueBuilder.append(")");

        sqlBuilder.append(columnBuilder.toString().replace(",)", ")"));
        sqlBuilder.append(" VALUES ");
        sqlBuilder.append(valueBuilder.toString().replace(",)", ")"));

        String sql = sqlBuilder.toString();
        logger.info("sql statement:" + sql);

        return sql;
    }
}
