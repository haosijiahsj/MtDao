package com.hhssjj.mt.entity;

import com.hhssjj.mt.support.SqlType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
@Entity
@Table(name = "mt_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Transient
    private String testTransient;

    @Enumerated(EnumType.STRING)
    @Column(name = "sqlType")
    private SqlType sqlType;


    @Column(name = "ts")
    private java.sql.Date updateTime;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public java.sql.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getTestTransient() {
        return testTransient;
    }

    public void setTestTransient(String testTransient) {
        this.testTransient = testTransient;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }
}
