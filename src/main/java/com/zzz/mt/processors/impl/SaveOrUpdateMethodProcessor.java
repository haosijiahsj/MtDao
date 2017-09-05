package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.SaveOrUpdate;
import com.zzz.mt.mapping.EntityScanner;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.zzz.mt.sql.impl.InsertSqlCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.sql.impl.UpdateSqlCreator;
import com.zzz.mt.support.SqlCreateType;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveOrUpdateMethodProcessor extends BaseMethodProcessor<SaveOrUpdate> {

    @Override
    public Object process() {
        return null;
    }

}
