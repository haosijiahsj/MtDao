package com.zzz.mt.jdbc;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public interface JdbcOperations {

    /**
     * 使用拼接的sql执行更新
     * @param sql
     * @return
     */
    int update(String sql);

    /**
     * 使用预处理语句执行更新
     * @param psql
     * @param parameters
     * @return
     */
    int update(String psql, Object...parameters);

    /**
     * 用户自定义预处理语句
     * @param creator
     * @return
     */
    int update(PreparedStatementCreator creator);

    /**
     * 通过拼接的sql执行查询，并将结果放入map中
     * @param sql
     * @return
     */
    Map<String, Object> queryForMap(String sql);

    /**
     * 通过预处理语句执行查询，并将结果放入map中
     * @param psql
     * @param parameters
     * @return
     */
    Map<String, Object> queryForMap(String psql, Object...parameters);

    /**
     * 通过拼接语句执行查询，并将结果放入List<Map>中
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryForList(String sql);

    /**
     * 通过预处理语句执行查询，并将结果放入List<Map>中
     * @param psql
     * @param parameters
     * @return
     */
    List<Map<String, Object>> queryForList(String psql, Object...parameters);

    /**
     * 自动映射结构到clazz中
     * @param sql
     * @param rowMapper
     * @param <T>
     * @return
     */
    <T> T queryForObject(String sql, RowMapper<T> rowMapper);

    /**
     * 用户自定义结果映射
     * @param psql
     * @param rowMapper
     * @param parameters
     * @param <T>
     * @return
     */
    <T> T queryForObject(String psql, RowMapper<T> rowMapper, Object...parameters);

    /**
     * 用户自定义结果映射
     * @param sql
     * @param rowMapper
     * @param <T>
     * @return
     */
    <T> List<T> queryForList(String sql, RowMapper<T> rowMapper);

    /**
     * 用户自定义结果映射
     * @param psql
     * @param rowMapper
     * @param parameters
     * @param <T>
     * @return
     */
    <T> List<T> queryForList(String psql, RowMapper<T> rowMapper, Object...parameters);

}
