package com.zzz.mt.dao;

import com.zzz.mt.annotations.db.Update;
import com.zzz.mt.entity.User;
import com.zzz.mt.annotations.db.IdColumn;

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
