package com.hhssjj.mt.reflect;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by 胡胜钧 on 8/7 0007.
 * 反射工具类
 */
public class ReflectUtils {

    private static Logger logger = Logger.getLogger(ReflectUtils.class);

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

    public static Field[] getDeclaredFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

    public static Method[] getDeclaredMethods(Object object) {
        return object.getClass().getDeclaredMethods();
    }

    public static Object invoke(Object object, String methodName) {
        Object value;
        try {
            value = object.getClass().getDeclaredMethod(methodName).invoke(object);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("can't invoke '" + methodName + "()' method, cause by : " +e.getMessage());
        }
        return value;
    }

}
