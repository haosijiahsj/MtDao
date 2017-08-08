package com.hhssjj.mt;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 胡胜钧 on 8/6 0006.
 */
@Table(name = "mt_user")
public class User {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    private Boolean sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")

    private String address;

    private boolean isDeleted;

    @Column(name = "ts")
    private java.sql.Date updateTime;

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
