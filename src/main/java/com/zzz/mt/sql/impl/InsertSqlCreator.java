package com.zzz.mt.sql.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zzz.mt.mapping.*;
import com.zzz.mt.sql.SingleParamSqlCreator;
import com.zzz.mt.utils.EntityCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class InsertSqlCreator extends SingleParamSqlCreator {

    private Logger logger = LoggerFactory.getLogger(InsertSqlCreator.class);

    public InsertSqlCreator() {}

    @Override
    public String generatePsql() {

        MapperEntity entity = this.getMapperEntity();

        MapperKey key = entity.getKey();
        List<MapperColumn> columns = entity.getColumns();
        List<MapperForeignKey> foreignKeys = entity.getForeignKeys();

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

        List<String> columnNames = Lists.newArrayList();
        List<String> questionMarks = Lists.newArrayList();
        List<Object> valueList = Lists.newArrayList();

        if (key != null && key.getValue() != null) {
            columnNames.add("`" + key.getColumnName() + "`");
            questionMarks.add("?");
            valueList.add(key.getValue());
        }

        for (MapperColumn column : columns) {
            columnNames.add("`" + column.getColumnName() + "`");
            questionMarks.add("?");
            valueList.add(column.getValue());
        }

        sqlBuilder.append("`").append(entity.getTableName()).append("`")
                .append("(").append(Joiner.on(", ").join(columnNames)).append(")")
                .append(" VALUES ")
                .append("(").append(Joiner.on(", ").join(questionMarks)).append(")");
        String psql = sqlBuilder.toString();
        super.values = new Object[valueList.size()];
        valueList.toArray(super.values);

        // 外键暂时不处理
        for (MapperForeignKey foreignKey : foreignKeys) {
        }

        logger.info("sql statement: {}", psql);
        logger.info("sql parameter: {}", this.values);
        return psql;
    }

    /**
     * 获取已经放入值的MapperEntity
     * @return
     */
    private MapperEntity getMapperEntity() {
        Class<?> clazz = parameter.getClass();
        // 先从缓存中获取
        MapperEntity entity = EntityCache.get(clazz);

        // 缓存中没有重新扫描，并放入缓存中
        if (entity== null) {
            entity = new MapperEntityScanner(clazz).scanEntity();
            EntityCache.put(clazz, entity);
        }

        MapperEntityHandler handler = new MapperEntityHandler(entity, parameter);
        handler.setValueFromObject();

        return handler.getMapperEntity();
    }

}
