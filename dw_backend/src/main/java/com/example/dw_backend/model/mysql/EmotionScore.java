package com.example.dw_backend.model.mysql;

import javax.persistence.*;

/**
 * @author xuedixuedi
 * 情感分数表
 */
@Entity
@Table(name = "emotion_score")
@org.hibernate.annotations.Table(appliesTo = "emotion_score", comment = "情感评分表")
public class EmotionScore {

    @Id
    @GeneratedValue
    private double emotionScore;

    @Column(nullable = true)
    private int count;

    public double getEmotionScore() {
        return emotionScore;
    }

    public int getCount() {
        return count;
    }
}
