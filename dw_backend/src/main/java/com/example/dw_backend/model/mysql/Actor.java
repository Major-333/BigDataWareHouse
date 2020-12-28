package com.example.dw_backend.model.mysql;

import com.example.dw_backend.model.mysql.pk.ActorPK;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 演员实体类：演员与演员参演的电影
 *
 * @author xuedixuedi
 */
@Data
@EqualsAndHashCode(exclude = {"movie"})
@Entity
@Table(name = "actor_movie")
@org.hibernate.annotations.Table(appliesTo = "actor_movie", comment = "演员参演电影表")
public class Actor {

    @EmbeddedId
    private ActorPK id;

    @JoinColumn(name = "product_id")
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private int movieCount;

    public String getActorName() {
        return id.getActorName();
    }

    public Movie getMovie() {
        return movie;
    }


    public int getMovieCount() {
        return movieCount;
    }
}
