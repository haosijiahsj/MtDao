package com.zzz.mt.annotations.db;

import java.lang.annotation.*;

/**
 * Created by 胡胜钧 on 8/7 0007.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchSave {
}
