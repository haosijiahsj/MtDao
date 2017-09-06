package com.zzz.mt.sql.impl;

import com.google.common.collect.Maps;
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
public class DeleteSqlCreator extends SingleParamSqlCreator {

    private Logger logger = LoggerFactory.getLogger(DeleteSqlCreator.class);

    public DeleteSqlCreator() {}

    @Override
    public String generatePsql() {
        return null;
    }

//    @Override
//    public String createPreparedSql() {
//        valueMap = Maps.newHashMap();
//        StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
//        MapperResult mapperResult = new MapperHandler(parameter, SqlType.UPDATE).getMapperResult();
//        List<MapperColumnResult> columnResults = mapperResult.getMapperColumnResults();
//
//        String idColumn = "";
//        Object idValue = null;
//        for (MapperColumnResult rs : columnResults) {
//            if (!rs.isId()) continue;
//            idColumn = rs.getColumnName();
//            idValue = rs.getValue();
//        }
//        valueMap.put(1, idValue);
//        sqlBuilder.append("`").append(mapperResult.getTableName()).append("`")
//                .append(" WHERE ").append("`").append(idColumn).append("` = ?");
//
//        String sql = sqlBuilder.toString();
//        logger.info("sql statement: ", sql);
//        return sql;
//    }

}
