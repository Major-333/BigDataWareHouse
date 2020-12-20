package com.example.dw_backend.model.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author xuedixuedi
 * 导演实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "director", comment = "导演表")
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String directorName;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @Column(nullable = true)
    private String movieCount;

    public String getDirectorName() {
        return directorName;
    }

    public String getMovieCount() {
        return movieCount;
    }

    public Movie getMovie() {
        return movie;
    }
}
