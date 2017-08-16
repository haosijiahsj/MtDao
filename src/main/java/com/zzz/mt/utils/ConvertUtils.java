package com.zzz.mt.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class ConvertUtils {

    /**
     * 判断传入的object类型，对值进行相应的转换，拆箱再装箱
     * @return
     */
    // Enumerated
    public static Object convert(Object object) {
        if (object instanceof Date) {
            return new Timestamp(((Date) object).getTime());
        } else if (object instanceof Enum) {
            return ((Enum) object).name();
        }
        return object;
    }

    private ConvertUtils() {}

}
