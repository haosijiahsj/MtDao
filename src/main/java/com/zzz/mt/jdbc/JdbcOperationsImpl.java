package com.zzz.mt.jdbc;

import com.zzz.mt.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class JdbcOperationsImpl implements JdbcOperations {

    private DataSource dataSource;

    public JdbcOperationsImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcOperationsImpl() {
        // 通过直接获取spring的bean来获取dataSource
    }


    @Override
    public int update(String sql) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int update(String psql, Object... parameters) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
             PreparedStatement pstatement = connection.prepareStatement(psql)) {

            int i = 0;
            for (Object parameter : parameters) {
                pstatement.setObject(++i, parameter);
            }

            return pstatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int update(PreparedStatementCreator creator) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            PreparedStatement pstatement = creator.createPreparedStatement(connection);
            return pstatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql) {
        return this.queryForList(sql).get(0);
    }

    @Override
    public Map<String, Object> queryForMap(String psql, Object... parameters) {
        return this.queryForList(psql, parameters).get(0);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            List<Map<String, Object>> maps = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < md.getColumnCount(); i++) {
                    String columnName = md.getColumnName(i);
                    map.put(columnName, rs.getObject(columnName));
                }
                maps.add(map);
            }

            return maps;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Map<String, Object>> queryForList(String psql, Object... parameters) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstatement = connection.prepareStatement(psql)) {

            int i = 0;
            for (Object parameter : parameters) {
                pstatement.setObject(++i, parameter);
            }

            ResultSet rs = pstatement.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            List<Map<String, Object>> maps = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < md.getColumnCount(); j++) {
                    String columnName = md.getColumnName(j);
                    map.put(columnName, rs.getObject(columnName));
                }
                maps.add(map);
            }

            return maps;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public <T> T queryForObject(Class<T> clazz, String sql) {
        return null;
    }

    @Override
    public <T> T queryForObject(Class<T> clazz, String psql, Object... parameters) {
        return queryForList(clazz, psql, parameters).get(0);
    }

    @Override
    public <T> List<T> queryForList(Class<T> clazz, String sql) {

        return null;
    }

    @Override
    public <T> List<T> queryForList(Class<T> clazz, String psql, Object... parameters) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstatement = connection.prepareStatement(psql)) {

            List<T> objects = new ArrayList<>();
            int i = 0;
            for (Object parameter : parameters) {
                pstatement.setObject(++i, parameter);
            }

            ResultSet rs = pstatement.executeQuery();
            while (rs.next()) {
                T value = (T) rs.getObject(1);
                objects.add(value);
            }
            return objects;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
        return queryForList(sql, rowMapper).get(0);
    }

    @Override
    public <T> T queryForObject(String psql, RowMapper<T> rowMapper, Object... parameters) {
        return queryForList(psql, rowMapper, parameters).get(0);
    }

    @Override
    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement()) {
            List<T> list = new ArrayList<>();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                T t =rowMapper.rowMapping(rs, rs.getMetaData().getColumnCount());
                list.add(t);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public <T> List<T> queryForList(String psql, RowMapper<T> rowMapper, Object... parameters) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstatement = connection.prepareStatement(psql)) {
            List<T> list = new ArrayList<>();

            int i = 0;
            for (Object parameter : parameters) {
                pstatement.setObject(++i, parameter);
            }

            ResultSet rs = pstatement.executeQuery();
            while (rs.next()) {
                T t = rowMapper.rowMapping(rs, rs.getMetaData().getColumnCount());
                list.add(t);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}
