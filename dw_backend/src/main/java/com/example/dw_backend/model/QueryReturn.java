package com.example.dw_backend.model;

import com.example.dw_backend.model.mysql.Movie;
import lombok.Data;

import java.util.List;

/**
 * 查询结果返回类
 *
 * @author xuedixuedi
 */
@Data
public class QueryReturn {
    long time;
    List<Movie> movieList;

    public QueryReturn(long time, List<Movie> movieList) {
        this.time = time;
        this.movieList = movieList;
    }
}

