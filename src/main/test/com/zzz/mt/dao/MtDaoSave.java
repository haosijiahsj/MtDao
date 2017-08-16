package com.zzz.mt.dao;

import com.zzz.mt.entity.User;
import com.zzz.mt.annotations.db.Save;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface MtDaoSave {
    @Save
    int saveFromEntity(User user);

    @Save(value = "INSERT INTO `mt_user`(`name`,`sex`,`age`,`address`,`ts`) VALUES (?,?,?,?,?)", returnId = true)
    int saveFromSql(String name, boolean sex, int age, String address, Timestamp ts);

    @Save(tableName = "mt_user")
    int saveFromMapWithTableName(Map<String, Object> map);

    @Save(entityClass = User.class)
    int saveFromMapWithEntityClass(Map<String, Object> map);

}
