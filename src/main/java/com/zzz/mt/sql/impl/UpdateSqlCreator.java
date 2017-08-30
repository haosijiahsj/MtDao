package com.zzz.mt.sql.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zzz.mt.mapping.MapperColumnResult;
import com.zzz.mt.mapping.MapperHandler;
import com.zzz.mt.mapping.MapperResult;
import com.zzz.mt.sql.SingleParamSqlCreator;
import com.zzz.mt.support.SqlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class UpdateSqlCreator extends SingleParamSqlCreator {

    private Logger logger = LoggerFactory.getLogger(UpdateSqlCreator.class);
    private String sql;

    public UpdateSqlCreator() {}

    @Override
    public String createUserSql() {
        valueMap = new HashMap<>();
        int i = 0;
        for (Object value : parameters) {
            valueMap.put(++i, value);
        }
        logger.info("sql statement: ", this.sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        valueMap = new HashMap<>();
        MapperResult mapperResult = new MapperHandler(parameter, SqlType.UPDATE).getMapperResult();
        List<MapperColumnResult> columnResults = mapperResult.getMapperColumnResults();
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
        sqlBuilder.append("`").append(mapperResult.getTableName()).append("`").append(" SET ");

        List<String> content = Lists.newArrayList();
        int i = 0;
        String idColumn = "";
        Object idValue = null;
        for (MapperColumnResult rs : columnResults) {
            if (rs.isId()) {
                idColumn = rs.getColumnName();
                idValue = rs.getValue();
                continue;
            }
            content.add("`" + rs.getColumnName() + "` = ?");
            valueMap.put(++i, rs.getValue());
        }
        valueMap.put(++i, idValue);
        sqlBuilder.append(Joiner.on(", ").join(content))
                .append(" WHERE ")
                .append("`").append(idColumn).append("` = ?");

        String sql = sqlBuilder.toString();
        logger.info("sql statement: ", sql);
        return sql;
    }

    @Override
    public String createPreparedSqlFromMap() {
        return null;
    }
}
