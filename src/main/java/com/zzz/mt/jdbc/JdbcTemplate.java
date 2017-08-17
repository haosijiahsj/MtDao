package com.zzz.mt.jdbc;

import com.zzz.mt.datasource.DataSourceUtils;
import com.zzz.mt.exceptions.MtDaoException;
import com.zzz.mt.mapping.EntityScanner;
import com.zzz.mt.mapping.PersistentEntity;
import com.zzz.mt.mapping.PersistentProperty;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class JdbcTemplate implements JdbcOperations {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate() {
        // 通过直接获取spring的bean来获取dataSource
    }


    @Override
    public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> callback) {
        return null;
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
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstatement = connection.prepareStatement(psql)) {

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
        return null;
    }

    @Override
    public <T> List<T> queryForList(Class<T> clazz, String sql) {

        return null;
    }

    @Override
    public <T> List<T> queryForList(Class<T> clazz, String psql, Object... parameters) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstatement = connection.prepareStatement(psql)) {

            int i = 0;
            for (Object parameter : parameters) {
                pstatement.setObject(++i, parameter);
            }

            ResultSet rs = pstatement.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            List<T> objects = new ArrayList<>();
            Map<String, Field> map = this.getMap(clazz);
            while (rs.next()) {
                T object = clazz.newInstance();
                for (int j = 0; j < md.getColumnCount(); j++) {
                    String dbColumnName = md.getColumnName(j);
                    Object value = rs.getObject(dbColumnName);
                    Field field = map.get(dbColumnName);
                }
                objects.add(object);
            }

            return objects;
        } catch (SQLException | ReflectiveOperationException e) {
            throw new RuntimeException();
        }
    }

    private Map<String, Field> getMap(Class<?> clazz) {
        Map<String, Field> map = new HashMap<>();
        PersistentEntity entity = new EntityScanner(clazz).getPersistentEntity();
        List<PersistentProperty> propertyList = entity.getPropertyList();
        for (PersistentProperty prop : propertyList) {
            map.put(prop.getColumnName(), prop.getField());
        }
        return map;
    }

}
