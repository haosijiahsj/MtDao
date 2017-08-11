package com.hhssjj.mt.sql;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class UpdateSqlCreator extends SqlCreator {

    private Logger logger = Logger.getLogger(UpdateSqlCreator.class);

    @Override
    public String createSql() {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
        String tableName = getTableName();
        Field[] fields = getFields();
        sqlBuilder.append("`" + tableName +"` SET ");
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
                        .getMethod(getMethodName)
                        .invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("不能找到" + getMethodName + "()方法，" + e.getMessage());
            }
            if (value == null) {
                continue;
            }
            if (column == null) {
                sqlBuilder.append("`" + fieldName + "`=");
            } else {
                sqlBuilder.append("`" + column.name() + "`=");
            }
            // 判断该字段的类型，拼装不同的值
            if (String.class.equals(value.getClass())) {
                sqlBuilder.append("'" + value + "',");
            } else if (boolean.class.equals(value.getClass()) || Boolean.class.equals(value.getClass())) {
                int tempValue = (boolean) value ? 1 :0;
                sqlBuilder.append(tempValue + ",");
            } else if (Date.class.equals(value.getClass()) ||
                    Timestamp.class.equals(value.getClass())) {
                long tempValue = ((Date) value).getTime();
                sqlBuilder.append("'" + tempValue + "',");
            } else {
                sqlBuilder.append(value + ",");
            }

        }

        Object id = getIdValue();
        sqlBuilder.append("WHERE `id` = " + id);
        String sql = sqlBuilder.toString().replace(",WHERE", " WHERE");
        logger.info("生成的更新sql为：" + sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
        String tableName = getTableName();
        Field[] fields = getFields();
        sqlBuilder.append("`" + tableName +"` SET ");
        int i = 0;
        valueMap = new HashMap<>();
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
                        .getMethod(getMethodName)
                        .invoke(parameter);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException("不能找到" + getMethodName + "()方法，" + e.getMessage());
            }
            if (value == null) {
                continue;
            }
            if (column == null) {
                sqlBuilder.append("`" + fieldName + "` = ?,");
            } else {
                sqlBuilder.append("`" + column.name() + "` = ?,");
            }
            valueMap.put(++i, value);

        }

        sqlBuilder.append("WHERE `id` = ?");
        valueMap.put(++i, getIdValue());
        String sql = sqlBuilder.toString().replace(",WHERE", " WHERE");
        logger.info("sql statement:" + sql);
        return sql;
    }

    @Override
    public String createPreparedSqlFromMap(String tableName) {
        return null;
    }
}
