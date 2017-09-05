package com.zzz.mt.sql.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zzz.mt.mapping.MapperHandler;
import com.zzz.mt.mapping.MapperColumnResult;
import com.zzz.mt.mapping.MapperResult;
import com.zzz.mt.sql.SingleParamSqlCreator;
import com.zzz.mt.support.SqlType;
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
    public String createPreparedSql() {
        MapperResult mapperResult = new MapperHandler(parameter, SqlType.INSERT).getMapperResult();

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

        List<MapperColumnResult> columnResults = mapperResult.getMapperColumnResults();

        List<String> questionMarks = Lists.newArrayList();
        List<String> columnNames = Lists.newArrayList();

        for (MapperColumnResult rs : columnResults) {
            columnNames.add("`" + rs.getColumnName() + "`");
            questionMarks.add("?");
        }

        sqlBuilder.append("`").append(mapperResult.getTableName()).append("`")
                .append("(").append(Joiner.on(", ").join(columnNames)).append(")")
                .append(" VALUES ")
                .append("(").append(Joiner.on(", ").join(questionMarks)).append(")");

        String sql = sqlBuilder.toString();
        logger.info("sql statement:{}", sql);

        return sql;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String createPreparedSqlFromMap() {
        return null;
    }
}
