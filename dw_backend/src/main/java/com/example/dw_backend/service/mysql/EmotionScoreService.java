package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.EmotionScoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmotionScoreService {
    private final EmotionScoreRepository emotionScoreRepository;

    public EmotionScoreService(EmotionScoreRepository emotionScoreRepository) {
        this.emotionScoreRepository = emotionScoreRepository;
    }

    public int parsingMovieCount(int score, boolean large) {
        int result = 0;
        List<Integer> resList = new ArrayList<>();
        long startTime = System.currentTimeMillis();    //获取开始时间
        resList = emotionScoreRepository.getMovieCount(score, large);
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("数据库查询时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
        if (resList.size() > 0) {
            result = resList.get(0);
        }
        return result;
    }


}
