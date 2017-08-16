package com.zzz.mt.sql.impl;

import com.google.common.collect.Maps;
import com.zzz.mt.mapping.MapperColumnResult;
import com.zzz.mt.mapping.MapperHandler;
import com.zzz.mt.mapping.MapperResult;
import com.zzz.mt.sql.SingleParamSqlCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.support.SqlType;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class DeleteSqlCreator extends SingleParamSqlCreator {

    private Logger logger = Logger.getLogger(DeleteSqlCreator.class);

    private String sql;

    public DeleteSqlCreator() {}

    @Override
    public String createUserSql() {
        valueMap = new HashMap<>();
        int i = 0;
        for (Object value : parameters) {
            valueMap.put(++i, value);
        }
        logger.info("sql statement: " + this.sql);
        return sql;
    }

    @Override
    public String createPreparedSql() {
        valueMap = Maps.newHashMap();
        StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
        MapperResult mapperResult = new MapperHandler(parameter, SqlType.UPDATE).getMapperResult();
        List<MapperColumnResult> columnResults = mapperResult.getMapperColumnResults();

        String idColumn = "";
        Object idValue = null;
        for (MapperColumnResult rs : columnResults) {
            if (!rs.isId()) continue;
            idColumn = rs.getColumnName();
            idValue = rs.getValue();
        }
        valueMap.put(1, idValue);
        sqlBuilder.append("`").append(mapperResult.getTableName()).append("`")
                .append(" WHERE ").append("`").append(idColumn).append("` = ?");

        String sql = sqlBuilder.toString();
        logger.info("sql statement: " + sql);
        return sql;
    }

    @Override
    public String createPreparedSqlFromMap() {
        return null;
    }
}
