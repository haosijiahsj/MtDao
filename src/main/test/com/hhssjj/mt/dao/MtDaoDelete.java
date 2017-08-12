package com.hhssjj.mt.dao;

import com.hhssjj.mt.entity.User;
import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.annotations.db.Update;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
public interface MtDaoDelete {

    @Delete
    int deleteFromEntity(User user);

    @Delete("delete from mt_user where id = ?")
    int deleteFromSql(int id);

}
