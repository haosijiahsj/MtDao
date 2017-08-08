package com.hhssjj.mt;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.annotations.db.Update;

import java.sql.Timestamp;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface TestDao {
    @Save
    int saveTest(User user);

    @Save(value = "INSERT INTO `mt_user`(`name`,`sex`,`age`,`address`,`ts`) VALUES (?,?,?,?,?)", returnId = true)
    int saveFromSqlTest(String name, boolean sex, int age, String address, Timestamp ts);

    @Delete
    int deleteTest(User user);

    @Update
    int updateTest(User user);

    @SaveOrUpdate
    void saveOrUpdateTest(User user);
}
