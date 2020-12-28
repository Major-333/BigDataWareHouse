package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.MovieRepository;
import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> parsingScoreList(int score, String comparison) {
        return this.movieRepository.getMovieCountByScore(score, comparison);
    }

    public List<Movie> parsingEmoScoreList(int score, String comparison) {
        return this.movieRepository.getMovieCountByEmotion(score, comparison);
    }
}
