package com.example.dw_backend.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体类
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user", comment = "用户表")
public class User {
    @Id
    @GeneratedValue
    private int user_id;

    private String profileName;
}
