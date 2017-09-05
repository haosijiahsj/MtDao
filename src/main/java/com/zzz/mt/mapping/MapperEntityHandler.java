package com.zzz.mt.mapping;

import com.zzz.mt.reflect.Reflection;
import com.zzz.mt.support.SqlType;

import java.util.List;

/**
 * Created by hushengjun on 2017/9/5.
 */
public class MapperEntityHandler {
    private MapperEntity mapperEntity;
    private Object object;
    private SqlType sqlType;

    public void setValue() {
        MapperKey key = mapperEntity.getKey();
        List<MapperColumn> columns = mapperEntity.getColumns();
        List<MapperForeignKey> foreignKeys = mapperEntity.getForeignKeys();

        // 获取主键的值
        key.setValue(Reflection.get(object, key.getField()));

        // 获取各列的值
        for (MapperColumn column : columns) {
            boolean flag = true;
            if (sqlType.equals(SqlType.INSERT)) {
                flag = column.isInsertable();
            } else if (SqlType.UPDATE.equals(sqlType) || SqlType.DELETE.equals(sqlType)) {
                flag = column.isUpdatable();
            }

            if (!flag) continue;

            column.setValue(Reflection.get(object, column.getField()));
        }

        // 获取外键相关的值，暂时先忽略
        for (MapperForeignKey foreignKey : foreignKeys) {
            boolean flag = false;
            if (sqlType.equals(SqlType.INSERT)) {
                flag = foreignKey.isInsertable();
            } else if (SqlType.UPDATE.equals(sqlType) || SqlType.DELETE.equals(sqlType)) {
                flag = foreignKey.isUpdatable();
            }
        }

    }

}
