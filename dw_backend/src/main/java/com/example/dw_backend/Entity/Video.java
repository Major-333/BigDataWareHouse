package com.example.dw_backend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author xuedixuedi
 * 视频类型实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "video", comment = "视频类型表")
public class Video {
    @Id
    @GeneratedValue
    private int videoId;

    @Column(nullable = true)
    private String videoType;

    public int getVideoId() {
        return videoId;
    }

    public String getVideoType() {
        return videoType;
    }
}
