package com.hhssjj.mt.configuration;

import com.hhssjj.mt.configuration.entity.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 胡胜钧 on 8/3 0003.
 */
public class ConfigHelper {
    private String propPrefix = "db.";

    public Config getConfig() {
        Config config = new Config();
        Properties properties = getProperties();
        config.setDriverClassName(properties.getProperty(propPrefix + "driverClassName"))
                .setUrl(properties.getProperty(propPrefix + "url"))
                .setUsername(properties.getProperty(propPrefix + "username"))
                .setPassword(properties.getProperty(propPrefix + "password"));
        // 其他判断，后面再说
        return config;
    }

    /**
     * 读取配置文件
     * @return
     */
    private Properties getProperties() {
        InputStream inputStream = ConfigHelper.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
