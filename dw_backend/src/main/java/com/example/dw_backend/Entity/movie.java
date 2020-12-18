package com.example.dw_backend.Entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author xuedixuedi
 * 电影实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "movie", comment = "电影表")
public class movie {
    @Id
    @GeneratedValue
    private int productId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Time.class)
    @JoinColumn(name = "time_id")
    @Column(nullable = true)
    private Time time;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Video.class)
    @JoinColumn(name = "video_id")
    @Column(nullable = true)
    private List<Video> video;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Score.class)
    @JoinColumn(name = "score_id")
    @Column(nullable = true)
    private Score score;

    @Column(nullable = true)
    private PNScore pnScore;

    @Column(nullable = true)
    private Director director;
}
