package com.example.dw_backend.model;

import lombok.Data;
import com.example.dw_backend.model.mysql.Movie;
import java.util.List;

@Data
public class QueryReturn {
    long time;
    List<Movie> movieList;

    public QueryReturn(long time, List<Movie> movieList) {
        this.time = time;
        this.movieList = movieList;
    }
}
