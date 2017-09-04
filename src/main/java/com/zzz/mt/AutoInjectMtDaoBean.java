package com.zzz.mt;

import com.zzz.mt.annotations.MtDao;
import com.zzz.mt.jdbc.JdbcOperations;
import com.zzz.mt.jdbc.JdbcOperationsImpl;
import com.zzz.mt.mapping.MapperEntity;
import com.zzz.mt.mapping.MapperEntityScanner;
import com.zzz.mt.reflect.Reflection;
import com.zzz.mt.utils.EntityCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Entity;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by 胡胜钧 on 8/4 0004.
 */
public class AutoInjectMtDaoBean implements BeanPostProcessor, Serializable {

    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.scanEntity(bean);
        Field[] fields = Reflection.getDeclaredFields(bean);
        // 扫描@Mt注解
        if (fields != null) {
            for (Field field : fields) {
                MtDao mtDaoAnnotation = field.getAnnotation(MtDao.class);
                if (mtDaoAnnotation == null) continue;
                // 使用自定义操作sql的语句
                JdbcOperations jdbcOperations = new JdbcOperationsImpl(dataSource);
                Object mtDao = MtDaoProxyFactory.create(field.getType(), jdbcOperations);
                field.setAccessible(true);
                try {
                    field.set(bean, mtDao);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("can't create a MtDao proxy for " + bean);
                }
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void scanEntity(Object bean) {
        MapperEntityScanner scanner = new MapperEntityScanner(bean.getClass());
        Entity entityAnno = bean.getClass().getAnnotation(Entity.class);
        if (entityAnno != null) {
            MapperEntity mapperEntity = scanner.scanEntity();
            // 放入缓存中
            EntityCache.put(bean.getClass(), mapperEntity);
        }
    }

}
