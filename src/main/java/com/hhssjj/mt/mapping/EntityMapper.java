package com.hhssjj.mt.mapping;

import com.hhssjj.mt.reflect.Reflection;
import com.hhssjj.mt.support.id.AutoCreateIdValue;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hushengjun on 2017/8/15.
 */
public class EntityMapper {

    private Object object;

    private Class<?> clazz;

    private PersistentEntity persistentEntity;

    public EntityMapper(Object object) {
        this.object = object;
        this.persistentEntity = new EntityScanner(object.getClass()).getPersistentEntity();
    }

    public EntityMapper(Class<?> clazz) {
        this.clazz = clazz;
        this.persistentEntity = new EntityScanner(clazz).getPersistentEntity();
    }

    private List<MapperForUpdate> handlePersistentEntityForValue() {
        List<PersistentProperty> propertyList = this.persistentEntity.getPropertyList();
        List<MapperForUpdate> mapperForUpdateList = new ArrayList<>();
        int i = 0;
        label : for (PersistentProperty prop : propertyList) {
            MapperForUpdate mapperForUpdate = new MapperForUpdate();

            Annotation[] annos = prop.getAnnotations();
            Field field = prop.getField();

            Object value = Reflection.get(object, field);
            for (Annotation anno : annos) {
                Class<? extends Annotation> annoType = anno.annotationType();

                if (Id.class.equals(anno.annotationType())) {
                    mapperForUpdate.setId(true);
                } else if (Column.class.equals(annoType)) {
                    Column column = (Column) anno;
                    if (!column.nullable()) throw new IllegalArgumentException( column.name()
                            + "can not be null");
                    mapperForUpdate.setColumnName(column.name());
                } else if (GeneratedValue.class.equals(annoType)) {
                    GeneratedValue generatedValue = (GeneratedValue) anno;
                    AutoCreateIdValue idValue = new AutoCreateIdValue(generatedValue);
                    value = idValue.createId();
                } else if (Enumerated.class.equals(annoType)) {
                    Enumerated enumerated = (Enumerated) anno;
                    EnumType type = enumerated.value();
                    Enum enumValue = (Enum) value;
                    value = EnumType.STRING.equals(type) ? enumValue.name() : enumValue.ordinal();
                } else if (Transient.class.equals(annoType)) {
                    continue label;
                }
            }
            mapperForUpdate.setIndex(++i);
            mapperForUpdate.setValue(value);

            mapperForUpdateList.add(mapperForUpdate);
        }

        return mapperForUpdateList;
    }

}
