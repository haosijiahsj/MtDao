package com.hhssjj.mt.dao;

import com.hhssjj.mt.annotations.db.*;
import com.hhssjj.mt.entity.User;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface MtDaoUpdate {

    @Update
    int updateFromEntity(User user);

    @Update("update mt_user set name = ?, sex = ?, age = ?, address= ? where id = ?")
    int updateFromSql(String name, boolean sex, int age, String address, int id);

    @Update(tableName = "mt_user")
    int updateFromMapWithTableName(@IdColumn("qw") Map<String, Object> map);

    @Update(entityClass = User.class)
    int updateFromMapWithEntityClass(@IdColumn("id") Map<String, Object> map);
}
