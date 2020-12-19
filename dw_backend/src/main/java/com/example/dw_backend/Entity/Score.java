package com.example.dw_backend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author xuedixuedi
 * 电影评分实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "score", comment = "电影评分表")
public class Score {
    @Id
    @GeneratedValue
    private int scoreId;

    @Column(nullable = true)
    private double score;

    @Column(nullable = true)
    private int count;

    public double getScore() {
        return score;
    }

    public int getCount() {
        return count;
    }

    public int getScoreId() {
        return scoreId;
    }


}
