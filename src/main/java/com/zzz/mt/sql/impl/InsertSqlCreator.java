package com.zzz.mt.sql.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zzz.mt.mapping.*;
import com.zzz.mt.sql.SingleParamSqlCreator;
import com.zzz.mt.support.SqlType;
import com.zzz.mt.utils.EntityCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class InsertSqlCreator extends SingleParamSqlCreator {

    private Logger logger = LoggerFactory.getLogger(InsertSqlCreator.class);

    private MapperEntity entity;

    public InsertSqlCreator(Object parameter) {
        super.parameter = parameter;
        MapperEntityHandler handler = new MapperEntityHandler(EntityCache.get(parameter.getClass()), parameter);
        this.entity = handler.getMapperEntity();
        this.generatePsqlAndValues();
    }

    private void generatePsqlAndValues() {
        MapperKey key = this.entity.getKey();
        List<MapperColumn> columns = this.entity.getColumns();
        List<MapperForeignKey> foreignKeys = this.entity.getForeignKeys();

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

        List<String> columnNames = Lists.newArrayList();
        List<String> questionMarks = Lists.newArrayList();
        List<Object> values = Lists.newArrayList();

        if (key != null && key.getValue() != null) {
            columnNames.add("`" + key.getColumnName() + "`");
            questionMarks.add("?");
            values.add(key.getValue());
        }

        for (MapperColumn column : columns) {
            columnNames.add("`" + column.getColumnName() + "`");
            questionMarks.add("?");
            values.add(column.getValue());
        }

        sqlBuilder.append("`").append(this.entity.getTableName()).append("`")
                .append("(").append(Joiner.on(", ").join(columnNames)).append(")")
                .append(" VALUES ")
                .append("(").append(Joiner.on(", ").join(questionMarks)).append(")");
        super.psql = sqlBuilder.toString();
        super.values = values.toArray();

        // 外键暂时不处理
        for (MapperForeignKey foreignKey : foreignKeys) {
            if (!foreignKey.isInsertable()) continue;

        }

        logger.info("sql statement: {}", this.psql);
        logger.info("sql parameter: {}", this.values);
    }

}
