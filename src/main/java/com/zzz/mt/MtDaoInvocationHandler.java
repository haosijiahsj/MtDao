package com.zzz.mt;

import com.zzz.mt.annotations.db.Delete;
import com.zzz.mt.annotations.db.Save;
import com.zzz.mt.annotations.db.SaveOrUpdate;
import com.zzz.mt.annotations.db.Update;
import com.zzz.mt.exceptions.AnnotationNotFoundException;
import com.zzz.mt.processors.BaseMethodProcessor;
import com.zzz.mt.processors.impl.DeleteMethodProcessor;
import com.zzz.mt.processors.impl.SaveMethodProcessor;
import com.zzz.mt.processors.impl.SaveOrUpdateMethodProcessor;
import com.zzz.mt.processors.impl.UpdateMethodProcessor;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class MtDaoInvocationHandler implements InvocationHandler {

    private Logger logger = Logger.getLogger(MtDaoInvocationHandler.class);

    private JdbcTemplate jdbcTemplate;

    public MtDaoInvocationHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Annotation[] methodAnnotations = method.getAnnotations();

        // 遍历方法上的注解
        for (Annotation annotation : methodAnnotations) {
            BaseMethodProcessor methodProcessor = null;
            Class<? extends Annotation> annotationType = annotation.annotationType();

            // 实例化不同的注解处理类
            if (Save.class.equals(annotationType)) {
                methodProcessor = new SaveMethodProcessor();
            } else if (Delete.class.equals(annotationType)) {
                methodProcessor = new DeleteMethodProcessor();
            } else if (Update.class.equals(annotationType)) {
                methodProcessor = new UpdateMethodProcessor();
            } else if (SaveOrUpdate.class.equals(annotationType)) {
                methodProcessor = new SaveOrUpdateMethodProcessor();
            }

            if (methodProcessor == null) {
                throw new IllegalStateException("not found proxy method, maybe it is't implemented");
            }

            // 给父抽象类中设置相关值
            methodProcessor.setJdbcTemplate(jdbcTemplate);
            methodProcessor.setMethodAnnotation(annotation);
            methodProcessor.setParameters(args);
            methodProcessor.setMethod(method);
            methodProcessor.setParameterAnnotations(method.getParameterAnnotations());

            return methodProcessor.process();
        }

        throw new AnnotationNotFoundException("in MtDao proxy interface not found method annotation like '@Save, @Update etc...'");
    }
}
