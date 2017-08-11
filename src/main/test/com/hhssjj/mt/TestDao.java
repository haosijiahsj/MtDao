package com.hhssjj.mt;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.annotations.db.Update;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface TestDao {
    @Save
    int saveTest(User user);

    @Save(value = "INSERT INTO `mt_user`(`name`,`sex`,`age`,`address`,`ts`) VALUES (?,?,?,?,?)", returnId = true)
    int saveFromSqlTest(String name, boolean sex, int age, String address, Timestamp ts);

    @Save(tableName = "mt_user")
    int saveFromMap(Map<String, Object> map);

    @Delete
    int deleteTest(User user);

    @Delete("delete from mt_user where id = ?")
    int deleteFromSqlTest(int id);

    @Update
    int updateTest(User user);

    @Update("update mt_user set name = ?, sex = ?, age = ?, address= ? where id = ?")
    int updateFromSql(String name, boolean sex, int age, String address, int id);

    @SaveOrUpdate
    void saveOrUpdateTest(User user);
}
