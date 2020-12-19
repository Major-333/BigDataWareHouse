package com.example.dw_backend.Entity;

import javax.persistence.*;

/**
 * 演员实体类：演员与演员参演的电影
 *
 * @author xuedixuedi
 */
@Entity
@Table(name = "actor_movie")
@org.hibernate.annotations.Table(appliesTo = "actor_movie", comment = "演员参演电影表")
public class ActorMovie {
    @Id
    @GeneratedValue
    private int id;

    private String actorName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private int movieCount;

    public Movie getMovie() {
        return movie;
    }

    public int getId() {
        return id;
    }

    public String getActorName() {
        return actorName;
    }

    public int getMovieCount() {
        return movieCount;
    }
}
