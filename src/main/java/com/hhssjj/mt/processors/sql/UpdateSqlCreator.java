package com.hhssjj.mt.processors.sql;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

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
            try {
                Object value = parameter.getClass()
                        .getMethod(getMethodName)
                        .invoke(parameter);
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
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                logger.error("反射执行get方法失败：" + e.getMessage());
            }
        }
        Object id = getIdValue();
        sqlBuilder.append("WHERE id = " + id);
        String sql = sqlBuilder.toString().replace(",WHERE", " WHERE");
        logger.info("生成的更新sql为：" + sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        return null;
    }
}
