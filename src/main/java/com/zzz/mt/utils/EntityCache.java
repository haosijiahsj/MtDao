package com.zzz.mt.utils;

import com.zzz.mt.mapping.MapperEntity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 胡胜钧 on 9/3 0003.
 */
public class EntityCache {
    private static ConcurrentHashMap<Class, MapperEntity> cache = new ConcurrentHashMap<>();

    public static void put(Class clazz, MapperEntity mapperEntity) {
        cache.put(clazz, mapperEntity);
    }
    public static MapperEntity get(Class clazz) {
        return cache.get(clazz);
    }

}
