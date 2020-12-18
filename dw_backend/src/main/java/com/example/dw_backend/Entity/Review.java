package com.example.dw_backend.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 评论实体类
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "review")
@org.hibernate.annotations.Table(appliesTo = "review", comment = "电影评论表")
public class Review {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private int up;

    private int down;

    private double score;

    private LocalDateTime time;

    @Lob
    private String summary;

    @Lob
    private String text;
}
