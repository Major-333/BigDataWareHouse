package com.example.dw_backend.Entity;

import javax.persistence.*;

/**
 * @author xuedixuedi
 * 情感分数表
 */
@Entity
@Table(name = "pn_score")
@org.hibernate.annotations.Table(appliesTo = "pn_score", comment = "情感评分表")
public class PNScore {
    @Id
    @GeneratedValue
    private int PNScoreId;
    @Column(nullable = true)
    private double PNScore;
    @Column(nullable = true)
    private int count;
}
