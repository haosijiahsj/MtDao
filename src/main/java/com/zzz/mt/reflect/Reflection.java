package com.zzz.mt.reflect;

import org.apache.log4j.Logger;

import java.lang.reflect.*;
import java.util.*;

import static com.zzz.mt.utils.Preconditions.checkArgument;
import static com.zzz.mt.utils.Preconditions.checkNotNull;

/**
 * Created by 胡胜钧 on 8/7 0007.
 * 反射工具类
 */
public class Reflection {

    private static Logger logger = Logger.getLogger(Reflection.class);

    /**
     * 判断是否是List类型的
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

    /**
     * 返回该类自己定义的字段
     * @param object
     * @return
     */
    public static Field[] getDeclaredFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    /**
     * 返回该类自己定义的方法
     * @param object
     * @return
     */
    public static Method[] getDeclaredMethods(Object object) {
        return object.getClass().getDeclaredMethods();
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    /**
     * 直接设置字段的值
     * @param o
     * @param value
     * @param field
     */
    public static void set(Object o, Object value, Field field) {
        field.setAccessible(true);
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * 直接获取字段的值
     * @param o
     * @param field
     * @return
     */
    public static Object get(Object o, Field field) {
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 通过方法名称执行方法
     * @param object
     * @param methodName
     * @return
     */
    public static Object invoke(Object object, String methodName) {
        Object value;
        try {
            value = object.getClass().getDeclaredMethod(methodName).invoke(object);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("can't invoke '" + methodName + "()' method, cause by : " +e.getMessage());
        }
        return value;
    }

    /**
     * 代理接口
     * @param interfaceType
     * @param handler
     * @param <T>
     * @return
     */
    public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        checkArgument(interfaceType.isInterface(), interfaceType + "is not a interface");
        Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class<?>[] { interfaceType },
                handler);
        return interfaceType.cast(object);
    }

    private Reflection() {}

}
