package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.Save;
import com.zzz.mt.processors.SingleParamMethodProcessor;
import com.zzz.mt.support.jdbcTemplate.MyPreparedStatementCreator;
import com.zzz.mt.sql.impl.InsertSqlCreator;
import com.zzz.mt.sql.SqlCreator;
import com.zzz.mt.support.SqlCreateType;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveMethodProcessor extends SingleParamMethodProcessor<Save> {

    @Override
    public Object process() {
        return null;
    }

}
