package com.example.dw_backend.model.mysql;

import com.example.dw_backend.model.mysql.pk.LabelPK;

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
public class Label {

    @EmbeddedId
    private LabelPK id;

    @JoinColumn(name = "product_id")
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    private int movieCount;

    public String getLabelName() {
        return id.getLabelName();
    }

    public Movie getMovie() {
        return movie;
    }

    public int getMovieCount() {
        return movieCount;
    }

}
