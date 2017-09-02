package com.zzz.mt.processors.impl;

import com.zzz.mt.annotations.db.NativeQuery;
import com.zzz.mt.processors.BaseMethodProcessor;

/**
 * Created by hushengjun on 2017/8/9.
 */
public class NativeQueryMethodProcessor extends BaseMethodProcessor<NativeQuery> {
    @Override
    public Object process() {
        String psql = methodAnnotation.value();
        Class<?> clazz = methodAnnotation.resultType();
        return null;
    }
}
