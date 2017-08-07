package com.hhssjj.mt;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.annotations.db.Update;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface TestDao {
    @Save(returnId = true)
    int saveTest(User user);

    @Delete
    int deleteTest(User user);

    @Update
    int updateTest(User user);

    @SaveOrUpdate
    void saveOrUpdateTest(User user);
}
