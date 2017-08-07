package com.hhssjj.mt;

import com.hhssjj.mt.annotations.MtDao;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
    public void test2() {
        User user = new User();
        user.setId(2L);
        int n = dao.deleteTest(user);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void test3() {
        User user = new User();
        user.setId(3L);
        user.setName("胡胜钧");
        user.setAge(21);
        user.setSex(true);
        user.setAddress("成都市");
        int n = dao.updateTest(user);
        System.out.println("n的值为：" + n);
    }

    @org.junit.Test
    public void test4() {
        User user = new User();
        user.setId(3L);
        user.setName("胡胜钧111");
        user.setAge(21);
        user.setSex(true);
        user.setAddress("成都111");
        dao.saveOrUpdateTest(user);
    }

}
