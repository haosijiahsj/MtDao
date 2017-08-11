package com.hhssjj.mt;

import com.hhssjj.mt.annotations.MtDao;
import com.hhssjj.mt.reflect.ReflectUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class)
public class Test {
    @MtDao
    private TestDao dao;

    @org.junit.Test
    public void test1() {
        User user = new User();
        user.setName("胡胜钧123");
        user.setAge(22);
        user.setSex(true);
        user.setAddress("成都市123");
        user.setUpdateTime(new java.sql.Date(new Date().getTime()));
        int n = dao.saveTest(user);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void saveFromSqlTest() {
        int n = dao.saveFromSqlTest("1", true, 3, "4", new Timestamp(new Date().getTime()));
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void test2() {
        User user = new User();
        user.setId(3L);
        int n = dao.deleteTest(user);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void deleteFromSqlTest() {
        int n = dao.deleteFromSqlTest(20);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void test3() {
        User user = new User();
        user.setId(20L);
        user.setName("胡胜钧");
        user.setAge(21);
        user.setSex(true);
        user.setAddress("成都市");
        int n = dao.updateTest(user);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void updateFromSql() {
        int n = dao.updateFromSql("hsj", false, 20, "cd", 21);
        System.out.println(n);
    }

    @org.junit.Test
    public void test4() {
        User user = new User();
        user.setId(3L);
        user.setName("胡胜钧");
        user.setAge(21);
        user.setSex(true);
        user.setAddress("成都");
        dao.saveOrUpdateTest(user);
    }

    @org.junit.Test
    public void testUtil() {
        List<User> list = new ArrayList<>();
        boolean flag = ReflectUtils.isList(list);
        boolean flag1 = ReflectUtils.isSet(list);
        Class clazz = ReflectUtils.getCollectionTypeClass(list);
        System.out.println(clazz);
    }

    @org.junit.Test
    public void testUtil1() {
        User user = new User();
        user.setId(12L);
        user.setUpdateTime(new java.sql.Date(new Date().getTime()));

        //ReflectUtils.getColumnAndValueMapFromObject(user);

    }

}
