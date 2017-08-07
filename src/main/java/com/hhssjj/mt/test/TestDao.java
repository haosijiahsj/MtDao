package com.hhssjj.mt.test;

import com.hhssjj.mt.annotations.db.Save;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface TestDao {
    @Save
    int saveTest(User user);
}
