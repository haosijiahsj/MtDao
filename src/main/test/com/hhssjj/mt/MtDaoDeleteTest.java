package com.hhssjj.mt;

import com.hhssjj.mt.annotations.MtDao;
import com.hhssjj.mt.dao.MtDaoDelete;
import com.hhssjj.mt.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class MtDaoDeleteTest extends MtDaoBaseTest {
    @MtDao
    private MtDaoDelete dao;

    @Test
    public void deleteFromEntityTest() {
        User user = new User();
        user.setId(3L);
        int n = dao.deleteFromEntity(user);
        System.out.println("n的值为：" + n);
    }

    @Test
    public void deleteFromSqlTest() {
        int n = dao.deleteFromSql(20);
        System.out.println("n的值为：" + n);
    }
}
