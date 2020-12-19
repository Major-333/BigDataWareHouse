package com.example.dw_backend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author xuedixuedi
 * 导演实体类
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "director", comment = "导演表")
public class Director {
    @Id
    @GeneratedValue
    private int directorId;
    @Column(nullable = true)
    private String directorName;
    @Column(nullable = true)
    private String movieCount;

    public int getDirectorId() {
        return directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getMovieCount() {
        return movieCount;
    }


}
