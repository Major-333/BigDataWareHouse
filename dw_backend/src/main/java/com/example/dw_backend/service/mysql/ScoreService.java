package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.MovieRepository;
import com.example.dw_backend.dao.mysql.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final MovieRepository movieRepository;

    public ScoreService(ScoreRepository scoreRepository, MovieRepository movieRepository) {
        this.scoreRepository = scoreRepository;
        this.movieRepository = movieRepository;
    }


}
