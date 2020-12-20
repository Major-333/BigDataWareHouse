package com.example.dw_backend.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author xuedixuedi
 * 视频版本实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "version", comment = "视频版本表")
public class Version {
    @Id
    @GeneratedValue
    private int versionId;

    @Column(nullable = true)
    private String versionType;

    public int getVersionId() {
        return versionId;
    }

    public String getVersionType() {
        return versionType;
    }
}
