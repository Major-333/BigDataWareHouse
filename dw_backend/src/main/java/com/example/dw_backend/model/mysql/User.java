package com.example.dw_backend.model.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体类
 *
 * @author xuedixuedi
 */
@Data
@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user", comment = "用户表")
public class User {
    @Id
    @GeneratedValue
    private String userId;

    private String profileName;

    public String getUserId() {
        return userId;
    }

    public String getProfileName() {
        return profileName;
    }

}
