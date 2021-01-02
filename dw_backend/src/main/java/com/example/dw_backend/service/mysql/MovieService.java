package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.MovieRepository;
import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private long scoreTime;
    private long emotionTime;
    private long titleTime;


    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> parsingScoreList(int score, String comparison) {
        List<Movie> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        result = this.movieRepository.getMovieCountByScore(score, comparison);
        long endTime = System.currentTimeMillis();    //获取结束时间
        this.scoreTime = endTime - startTime;

        return result;
    }

    public List<Movie> parsingEmoScoreList(int score, String comparison) {
        List<Movie> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        result = this.movieRepository.getMovieCountByEmotion(score, comparison);
        long endTime = System.currentTimeMillis();    //获取结束时间
        this.emotionTime = endTime - startTime;

        return result;
    }

    public List<Movie> parsingTitleList(String title) {
        List<Movie> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        result = this.movieRepository.findAllByTitle(title);
        long endTime = System.currentTimeMillis();    //获取结束时间
        this.titleTime = endTime - startTime;

        return result;
    }

    public long getScoreTime() {
        return scoreTime;
    }

    public long getEmotionTime() {
        return emotionTime;
    }

    public long getTitleTime() {
        return titleTime;
    }
}
