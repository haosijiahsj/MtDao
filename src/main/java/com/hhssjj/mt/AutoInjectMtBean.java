package com.hhssjj.mt;

import com.hhssjj.mt.annotations.MtDao;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class AutoInjectMtBean implements BeanPostProcessor, Serializable {

    private Logger logger = Logger.getLogger(AutoInjectMtBean.class);

    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private DataSource dataSource;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        // 扫描@Mt注解
        if (fields != null) {
            for (Field field : fields) {
                MtDao mtDaoAnnotation = field.getAnnotation(MtDao.class);
                if (mtDaoAnnotation == null) {
                    continue;
                }
                Object mtDao = MtProxyFactory.create(field.getType(), jdbcTemplate);
                field.setAccessible(true);
                try {
                    field.set(bean, mtDao);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
