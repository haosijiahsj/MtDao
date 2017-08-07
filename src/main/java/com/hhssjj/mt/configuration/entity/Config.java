package com.hhssjj.mt.configuration.entity;

/**
 * Created by 胡胜钧 on 8/3 0003.
 */
public class Config {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String validationQuery = "SELECT 1";
    private Integer maxActive = 100;
    private Integer maxWait = 9000;
    private Integer initSize = 10;
    // 最小空闲连接
    private Integer minIdle = 5;
    private Integer maxIdle = 20;
    // 回收线程等待时间
    private Integer timeBetweenEvictionRunsMillis = 9000;
    private Integer minEvictableIdleTimeMillis = 9000;

    public String getDriverClassName() {
        return driverClassName;
    }

    public Config setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Config setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Config setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Config setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public Config setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
        return this;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public Config setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public Config setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public Config setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public Config setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public Integer getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public Config setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        return this;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public Config setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        return this;
    }

    public Integer getInitSize() {
        return initSize;
    }

    public Config setInitSize(Integer initSize) {
        this.initSize = initSize;
        return this;
    }
}
