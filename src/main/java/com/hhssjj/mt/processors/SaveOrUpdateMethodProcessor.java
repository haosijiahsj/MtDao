package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.mapping.EntityMapping;
import com.hhssjj.mt.processors.creator.MyPreparedStatementCreator;
import com.hhssjj.mt.sql.InsertSqlCreator;
import com.hhssjj.mt.sql.SqlCreator;
import com.hhssjj.mt.sql.UpdateSqlCreator;
import com.hhssjj.mt.support.SqlCreateType;
import com.hhssjj.mt.support.SqlType;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveOrUpdateMethodProcessor extends BaseMethodProcessor<SaveOrUpdate> {

    @Override
    public Object process() {
        EntityMapping entityMapping = new EntityMapping(parameters[0]);
        Object idValue = entityMapping.getIdValue();
        SqlCreator sqlCreator;
        if (idValue == null) {
            sqlCreator = new InsertSqlCreator();
            sqlCreator.setParameter(parameters[0]);
            sqlCreator.setParameters(parameters);
            sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.INSERT));

        } else {
            sqlCreator = new UpdateSqlCreator();
            sqlCreator.setParameter(parameters[0]);
            sqlCreator.setParameters(parameters);
            sqlCreator.setEntityMapping(new EntityMapping(parameters[0], SqlType.UPDATE));
        }

        MyPreparedStatementCreator myPreparedStatementCreator =
                new MyPreparedStatementCreator(sqlCreator, SqlCreateType.AUTO_CREATE);
        return jdbcTemplate.update(myPreparedStatementCreator);
    }

}
