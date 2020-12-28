package com.example.dw_backend.bootstrap;

import com.example.dw_backend.dao.mysql.DirectorRepository;
import com.example.dw_backend.dao.mysql.EmotionScoreRepository;
import com.example.dw_backend.model.mysql.Director;
import com.example.dw_backend.service.mysql.DirectorService;
import com.example.dw_backend.service.mysql.EmotionScoreService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

@Slf4j
@Component
public class MovieBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DirectorRepository directorRepository;
    private final EmotionScoreRepository emotionScoreRepository;

    public MovieBootstrap(DirectorRepository directorRepository, EmotionScoreRepository emotionScoreRepository) {
        this.directorRepository = directorRepository;
        this.emotionScoreRepository = emotionScoreRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
