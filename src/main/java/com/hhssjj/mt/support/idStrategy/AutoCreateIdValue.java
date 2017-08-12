package com.hhssjj.mt.support.idStrategy;

import com.hhssjj.mt.utils.SqlUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * Created by hushengjun on 2017/8/11.
 */
public class AutoCreateIdValue {

    private GeneratedValue generatedValueAnnotation;

    public AutoCreateIdValue(GeneratedValue generatedValueAnnotation) {
        this.generatedValueAnnotation = generatedValueAnnotation;
    }

    public Object createId() {
        Object idValue;
        if (generatedValueAnnotation == null) {
            idValue = null;
        } else {
            GenerationType generationType = generatedValueAnnotation.strategy();

            // 自增长类型
            if (GenerationType.IDENTITY.equals(generationType)) {
                idValue = null;
            } else if (GenerationType.AUTO.equals(generationType)) {
                // 自动判断数据库类型，然后使用不同的主键生成策略
                if (!SqlUtils.isCurDatabaseType("mysql")) {
                    throw new IllegalStateException("sorry! at present MtDao can't support "
                            + generationType + " id generate strategy");
                } else {
                    idValue = 10000000;
                }
            } else if (GenerationType.SEQUENCE.equals(generationType)) {
                // Oracle数据库的主键生成策略
                throw new IllegalStateException("sorry! at present MtDao can't support "
                        + generationType + " id generate strategy");
            } else {
                // 这个需要维护一张表
                throw new IllegalStateException("sorry! at present MtDao can't support "
                        + generationType + " id generate strategy");
            }
        }
        return idValue;
    }

}
