package com.example.dw_backend.model.mysql;

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

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getScore() {
        return score;
    }

    public int getDown() {
        return down;
    }

    public int getUp() {
        return up;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }
}
