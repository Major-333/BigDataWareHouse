package com.example.dw_backend.Entity;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;

/**
 * 电影标签实体类
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "label_movie")
@org.hibernate.annotations.Table(appliesTo = "label_movie", comment = "电影标签表")
public class LabelMovie {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private String labelName;

    private int movieCount;

    public Movie getMovie() {
        return movie;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public int getId() {
        return id;
    }

    public String getLabelName() {
        return labelName;
    }

}
