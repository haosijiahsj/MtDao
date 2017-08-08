package com.hhssjj.mt.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * Created by 胡胜钧 on 8/7 0007.
 * 反射工具类
 */
public class ReflectUtils {

    /**
     * 判断是否是Set类型的
     * @param object
     * @return
     */
    public static boolean isList(Object object) {
        return object.getClass().isAssignableFrom(List.class);
    }

    /**
     * 判断是否是Set类型
     * @param object
     * @return
     */
    public static boolean isSet(Object object) {
        return object.getClass().isAssignableFrom(Set.class);
    }

    public static Class getCollectionTypeClass(Object object) {
        if (!isList(object) && !isSet(object)) {
            return null;
        }
        try {
            Method method = object.getClass().getMethod("get", object.getClass());
            Type type = method.getGenericReturnType();
            ParameterizedType pt = (ParameterizedType) type;
            return (Class) pt.getActualTypeArguments()[0];
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
