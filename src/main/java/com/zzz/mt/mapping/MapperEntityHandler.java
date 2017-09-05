package com.zzz.mt.mapping;

import com.zzz.mt.reflect.Reflection;

import java.util.List;

/**
 * Created by hushengjun on 2017/9/5.
 */
public class MapperEntityHandler {
    private MapperEntity mapperEntity;
    private Object object;

    public MapperEntityHandler(MapperEntity mapperEntity, Object object) {
        this.mapperEntity = mapperEntity;
        this.object = object;
    }

    public void setValue() {
        MapperKey key = mapperEntity.getKey();
        List<MapperColumn> columns = mapperEntity.getColumns();
        List<MapperForeignKey> foreignKeys = mapperEntity.getForeignKeys();

        // 获取主键的值
        key.setValue(Reflection.get(object, key.getField()));

        // 获取各列的值
        for (MapperColumn column : columns) {
            column.setValue(Reflection.get(object, column.getField()));
        }

        // 获取外键相关的值
        for (MapperForeignKey foreignKey : foreignKeys) {
            foreignKey.setValue(Reflection.get(object, key.getField()));
        }

    }

    public MapperEntity getMapperEntity() {
        return this.mapperEntity;
    }

}
