package com.zzz.mt.annotations.db;

import java.lang.annotation.*;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdColumn {
    String value();
}
