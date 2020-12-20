package com.example.dw_backend.model.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 电影标签实体类
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "label_movie")
@org.hibernate.annotations.Table(appliesTo = "label_movie", comment = "电影标签表")
public class Label implements Serializable {
    //为了保证两个共同id，要加这一行
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @Id
    private String labelName;

    private int movieCount;

    public Movie getMovie() {
        return movie;
    }

    public int getMovieCount() {
        return movieCount;
    }


    public String getLabelName() {
        return labelName;
    }

}
