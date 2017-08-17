package com.zzz.mt.jdbc;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public interface JdbcOperations {

    <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> callback);

    int update(String sql);

    int update(String psql, Object...parameters);

    Map<String, Object> queryForMap(String sql);

    Map<String, Object> queryForMap(String psql, Object...parameters);

    List<Map<String, Object>> queryForList(String sql);

    List<Map<String, Object>> queryForList(String psql, Object...parameters);

    <T> T queryForObject(Class<T> clazz, String sql);

    <T> T queryForObject(Class<T> clazz, String psql, Object... parameters);

    <T> List<T> queryForList(Class<T> clazz, String sql);

    <T> List<T> queryForList(Class<T> clazz, String psql, Object... parameters);

}
