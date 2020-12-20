package com.example.dw_backend.model.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 演员实体类：演员与演员参演的电影
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "actor_movie")
@org.hibernate.annotations.Table(appliesTo = "actor_movie", comment = "演员参演电影表")
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String actorName;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private int movieCount;

    public Movie getMovie() {
        return movie;
    }


    public String getActorName() {
        return actorName;
    }

    public int getMovieCount() {
        return movieCount;
    }
}
