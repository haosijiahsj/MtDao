package com.hhssjj.mt.jdbc;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public interface JdbcOperations {

    int update(String sql);

    int update(PreparedStatementCreator preparedStatementCreator);

    <T> T queryForObject(String sql, RowMapper<T> rowMapper);

    <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper);

    Map<String, Object> queryForMap(String sql);

    <T> List<T> queryForList(String sql, RowMapper<T> rowMapper);

    List<Map<String, Object>> queryForList(String sql);

    <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> callback);

}
