package com.example.dw_backend.model.mysql;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xuedixuedi
 * 情感分数表
 */
@Data
@Entity
@Table(name = "emotion_score")
@org.hibernate.annotations.Table(appliesTo = "emotion_score", comment = "情感评分表")
public class EmotionScore {

    @Id
    @GeneratedValue
    private int emotionScore;

    @Column(nullable = true)
    private int count;

    public int getEmotionScore() {
        return emotionScore;
    }

    public int getCount() {
        return count;
    }
}
