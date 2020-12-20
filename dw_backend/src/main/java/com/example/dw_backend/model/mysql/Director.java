package com.example.dw_backend.model.mysql;

import com.example.dw_backend.model.mysql.pk.DirectorPK;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author xuedixuedi
 * 导演实体类
 */
@Data
@EqualsAndHashCode(exclude = { "movie" })
@Entity
@org.hibernate.annotations.Table(appliesTo = "director", comment = "导演表")
public class Director {

    @EmbeddedId
    private DirectorPK id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    private Movie movie;

    @Column(nullable = true)
    private String movieCount;

    public String getMovieCount() {
        return movieCount;
    }

    public Movie getMovie() {
        return movie;
    }
}
