package com.example.dw_backend.Entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author xuedixuedi
 * 电影实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "movie", comment = "电影表")
public class Movie {
    @Id
    @GeneratedValue
    private int productId;

    @Column(nullable = true)
    private String title;

    @ManyToOne
    private Time time;

    @ManyToOne
    private Score score;

    @ManyToOne
    private Video video;

    @ManyToOne
    private EmotionScore emotionScore;

    @ManyToOne
    private Director director;
}
