package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.EmotionScoreRepository;
import com.example.dw_backend.model.mysql.EmotionScore;
import com.example.dw_backend.model.mysql.Score;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EmotionScoreService {
    private final EmotionScoreRepository emotionScoreRepository;

    public EmotionScoreService(EmotionScoreRepository emotionScoreRepository) {
        this.emotionScoreRepository = emotionScoreRepository;
    }

    public HashMap<String, Integer> parserCount(int sco, String comparison) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = 0;
        if (emotionScoreRepository.getMovieCount(sco, comparison).size() > 0) {
            result = emotionScoreRepository.getMovieCount(sco, comparison).get(0);
        }
        map.put("Count", result);
        return map;
    }

    public List<EmotionScore> findAll(){
        return this.emotionScoreRepository.findAll();
    }


}
