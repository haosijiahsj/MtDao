package com.hhssjj.mt.annotations.db.query;

import java.lang.annotation.*;

/**
 * Created by 胡胜钧 on 8/2 0002.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {
    boolean userCache() default false;
}
