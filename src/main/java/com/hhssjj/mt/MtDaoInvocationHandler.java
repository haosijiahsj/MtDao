package com.hhssjj.mt;

import com.hhssjj.mt.annotations.db.Delete;
import com.hhssjj.mt.annotations.db.Save;
import com.hhssjj.mt.annotations.db.SaveOrUpdate;
import com.hhssjj.mt.annotations.db.Update;
import com.hhssjj.mt.processors.*;
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] methodAnnotations = method.getAnnotations();

        // 遍历方法上的注解，实例化不同的处理类
        for (Annotation annotation : methodAnnotations) {
            BaseMethodProcessor methodProcessor = null;
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (Save.class.equals(annotationType)) {
                methodProcessor = new SaveMethodProcessor();
            } else if (Delete.class.equals(annotationType)) {
                methodProcessor = new DeleteMethodProcessor();
            } else if (Update.class.equals(annotationType)) {
                methodProcessor = new UpdateMethodProcessor();
            } else if (SaveOrUpdate.class.equals(annotationType)) {
                methodProcessor = new SaveOrUpdateMethodProcessor();
            } else {

            }

            // 给父抽象类中设置相关值
            if (methodProcessor != null) {
                methodProcessor.setJdbcTemplate(jdbcTemplate);
                methodProcessor.setMethodAnnotation(annotation);
                methodProcessor.setParameters(args);
                methodProcessor.setMethod(method);
                methodProcessor.setParameterAnnotations(method.getParameterAnnotations());

                return methodProcessor.process();
            }
        }

        throw new IllegalStateException("没有找到相关代理方法");
    }
}
