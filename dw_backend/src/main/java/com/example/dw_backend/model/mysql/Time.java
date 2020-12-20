package com.example.dw_backend.model.mysql;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xuedixuedi
 * 时间实体类
 */
@Data
@Entity
@org.hibernate.annotations.Table(appliesTo = "time", comment = "日期表")
public class Time {

    @Id
    @GeneratedValue
    private int timeId;

    @Column(nullable = true)
    private int year;

    @Column(nullable = true)
    private int month;

    @Column(nullable = true)
    private int day;

    @Column(nullable = true)
    private int season;

    private int yearCount;

    private int monthCount;

    private int dayCount;

    private int seasonCount;

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }
}
