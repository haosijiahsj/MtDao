package com.zzz.mt.utils;

import com.sun.istack.internal.Nullable;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class Preconditions {

    /**
     * 参数检查
     * @param expression
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 参数检查
     * @param expression
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * 空检查
     * @param reference
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 空检查
     * @param reference
     * @param errorMessage
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    private Preconditions() {}

}
