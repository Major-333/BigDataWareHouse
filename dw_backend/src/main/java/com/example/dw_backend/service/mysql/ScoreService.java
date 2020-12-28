package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.MovieRepository;
import com.example.dw_backend.dao.mysql.ScoreRepository;
import com.example.dw_backend.model.mysql.Score;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository, MovieRepository movieRepository) {
        this.scoreRepository = scoreRepository;
    }

    public HashMap<String,Integer> parserCount(int sco, String comparison) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = 0;
        if (scoreRepository.getMovieCount(sco, comparison).size() > 0) {
            result = scoreRepository.getMovieCount(sco, comparison).get(0);
        }
        map.put("Count", result);
        return map;
    }

    public List<Score> findAll(){
        return this.scoreRepository.findAll();
    }


}
