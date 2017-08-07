package com.hhssjj.mt.processors;

import com.hhssjj.mt.annotations.db.SaveOrUpdate;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class SaveOrUpdateMethodProcessor extends BaseMethodProcessor<SaveOrUpdate> {

    @Override
    public Object process() {
        Object id = null;
        try {
            id = parameters[0].getClass()
                    .getDeclaredMethod("getId")
                    .invoke(parameters[0]);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        BaseMethodProcessor methodProcessor;
        // 通过实体类中的id是否存在来进行判断，存在则更新不存在则插入
        if (id == null) {
            methodProcessor = new SaveMethodProcessor();
        } else {
            methodProcessor = new UpdateMethodProcessor();
        }
        methodProcessor.setJdbcTemplate(jdbcTemplate);
        methodProcessor.setMethodAnnotation(methodAnnotation);
        methodProcessor.setParameters(parameters);
        methodProcessor.setParameterAnnotation(parameterAnnotation);
        methodProcessor.setMethod(method);

        return methodProcessor.process();
    }
}
