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
        return List.class.isAssignableFrom(object.getClass());
    }

    /**
     * 判断是否是Set类型
     * @param object
     * @return
     */
    public static boolean isSet(Object object) {
        return Set.class.isAssignableFrom(object.getClass());
    }

    /**
     * 返回泛型类型的class
     * @param object
     * @return
     */
    public static Class<?> getCollectionTypeClass(Object object) {
        try {
            Method method = object.getClass().getDeclaredMethod("get", int.class);
            return method.getReturnType();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
