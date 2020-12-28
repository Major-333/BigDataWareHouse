package com.example.dw_backend.model.mysql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * @author xuedixuedi
 * 电影实体类
 */
@Data
@EqualsAndHashCode(exclude = {"time", "score", "emotionScore"})
@Entity
@org.hibernate.annotations.Table(appliesTo = "movie", comment = "电影表")
public class Movie {
    @Id
    @GeneratedValue
    private String productId;

    @Column(nullable = true)
    private String titleY;

    @Column(nullable = true)
    private int version;

    @ManyToOne
    private Time time;

    @ManyToOne
    private Score score;

    @ManyToOne
    private EmotionScore emotionScore;

}
