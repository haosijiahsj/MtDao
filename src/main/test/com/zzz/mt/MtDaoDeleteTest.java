package com.zzz.mt;

import com.zzz.mt.annotations.MtDao;
import com.zzz.mt.dao.MtDaoDelete;
import com.zzz.mt.entity.User;
import org.junit.Test;

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
