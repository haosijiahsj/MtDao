package com.hhssjj.mt.sql.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hhssjj.mt.mapping.EntityMapper;
import com.hhssjj.mt.mapping.MapperColumnResult;
import com.hhssjj.mt.mapping.MapperResult;
import com.hhssjj.mt.sql.SingleParamSqlCreator;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */
public class InsertSqlCreator extends SingleParamSqlCreator {

    private Logger logger = Logger.getLogger(InsertSqlCreator.class);
    private String sql;

    public InsertSqlCreator() {}

    @Override
    public String createUserSql() {
        valueMap = new HashMap<>();
        int i = 0;
        for (Object value : parameters) {
            valueMap.put(++i, value);
        }
        logger.info("sql statement: " + this.sql);
        return this.sql;
    }

    @Override
    public String createPreparedSql() {
        valueMap = new HashMap<>();
        MapperResult mapperResult = new EntityMapper(parameter).getMapperResult();

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

        List<MapperColumnResult> columnResults = mapperResult.getMapperColumnResults();

        List<String> questionMarks = Lists.newArrayList();
        List<String> columnNames = Lists.transform(columnResults, input -> {
            questionMarks.add("?");
            valueMap.put(input.getIndex(), input.getValue());
            return "`" + input.getColumnName() + "`";
        });

        sqlBuilder.append("`").append(mapperResult.getTableName()).append("`")
                .append("(").append(Joiner.on(", ").join(columnNames)).append(")")
                .append(" VALUES ")
                .append("(").append(Joiner.on(", ").join(questionMarks)).append(")");

        String sql = sqlBuilder.toString();
        logger.info("sql statement:" + sql);

        return sql;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String createPreparedSqlFromMap() {
        return sql;
    }
}
