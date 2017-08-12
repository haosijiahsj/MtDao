package com.hhssjj.mt;

import com.hhssjj.mt.annotations.MtDao;
import com.hhssjj.mt.dao.MtDaoUpdate;
import com.hhssjj.mt.entity.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */

public class MtDaoUpdateTest extends MtDaoBaseTest {

    @MtDao
    private MtDaoUpdate dao;

    @Test
    public void updateFromEntityTest() {
        User user = new User();
        user.setId(53L);
        user.setName("胡胜钧");
        user.setAge(21);
        user.setSex(true);
        user.setAddress("成都市");
        int n = dao.updateFromEntity(user);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void updateFromSqlTest() {
        int n = dao.updateFromSql("hsj", false, 20, "cd", 54);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void updateFromMapWithTableNameTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 55L);
        map.put("name", "hushengjun");
        int n = dao.updateFromMapWithTableName(map);
        System.out.println("返回的值为：" + n);
    }

    @Test
    public void updateFromMapWithEntityClassTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 56L);
        map.put("name", "hushengjun");
        int n = dao.updateFromMapWithEntityClass(map);
        System.out.println("返回的值为：" + n);
    }

}
