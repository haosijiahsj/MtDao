package com.hhssjj.mt.test;

import com.hhssjj.mt.annotations.MtDao;
import org.springframework.stereotype.Service;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
@Service
public class MtService {
    @MtDao
    private TestDao dao;

    public int test1() {
        User user = new User();
        user.setName("胡胜钧");
        user.setAge(22);
        user.setAddress("成都市");
        int n = dao.saveTest(user);
        return n;
    }

}
