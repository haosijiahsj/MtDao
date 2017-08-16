package com.zzz.mt.dao;

import com.zzz.mt.entity.User;
import com.zzz.mt.annotations.db.Delete;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface MtDaoDelete {

    @Delete
    int deleteFromEntity(User user);

    @Delete("delete from mt_user where id = ?")
    int deleteFromSql(int id);

}
