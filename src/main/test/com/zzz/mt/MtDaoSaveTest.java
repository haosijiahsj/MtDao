package com.zzz.mt;

import com.zzz.mt.annotations.MtDao;
import com.zzz.mt.dao.MtDaoSave;
import com.zzz.mt.entity.User;
import com.zzz.mt.mapping.MapperHandler;
import com.zzz.mt.mapping.MapperResult;
import com.zzz.mt.support.SqlType;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public class MtDaoSaveTest extends MtDaoBaseTest {
    @MtDao
    private MtDaoSave dao;

    @Test
    public void saveFromEntityTest() {
        User user = new User();
        user.setName("胡胜钧123");
        user.setAge(22);
        user.setSex(true);
        user.setAddress("成都市123");
        user.setUpdateTime(new java.sql.Date(new Date().getTime()));
        user.setSqlType(SqlType.INSERT);
        int n = dao.saveFromEntity(user);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void saveFromSqlTest() {
        int n = dao.saveFromSql("hsj", true, 22, "成都", new Timestamp(new Date().getTime()));
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void saveFromMapWithTableNameTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hhssjj");
        map.put("sex", true);
        map.put("age", 12);
        int n = dao.saveFromMapWithTableName(map);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void saveFromMapWithEntityClass() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hhssjj");
        map.put("sex", true);
        map.put("age", 12);
        int n = dao.saveFromMapWithEntityClass(map);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void testScanner() {
        User user = new User();
        user.setName("胡胜钧123");
        user.setAge(22);
        user.setSex(true);
        user.setAddress("成都市123");
        user.setTestTransient("testTransient");
        user.setSqlType(SqlType.INSERT);
        user.setUpdateTime(new java.sql.Date(new Date().getTime()));

        MapperResult mapperResult = new MapperHandler(user, SqlType.INSERT).getMapperResult();
    }

}
