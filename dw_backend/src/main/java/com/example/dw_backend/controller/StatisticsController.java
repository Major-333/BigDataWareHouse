package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.EmotionScore;
import com.example.dw_backend.model.mysql.Score;
import com.example.dw_backend.service.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {

    @Autowired
    private TimeService timeService;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private EmotionScoreService emotionScoreService;
    @Autowired
    private ScoreService scoreService;

    @ResponseBody
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public HashMap<String, Integer> getCountByTime(@RequestParam String time, @RequestParam String type, @RequestParam String comparison) {
        HashMap<String, Integer> result = new HashMap<>();
        String[] ymd = time.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);
        int season = day / 4 + 1;
        switch (type) {
            case "year":
                result = timeService.parserYearCount(year, comparison);
                break;
            case "month":
                result = timeService.parserMonthCount(year, month, comparison);
                break;
            case "day":
                result = timeService.parserDayCount(year, month, day, comparison);
                break;
            case "season":
                result = timeService.parserSeasonCount(year, season);
                break;
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/emotion", method = RequestMethod.GET)
    public HashMap<String, Integer> getCountByEmotion(int score, String large) {
        return emotionScoreService.parserCount(score, large);
    }

    @ResponseBody
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public HashMap<String, Integer> getCountByScore(int score, String large) {
        return scoreService.parserCount(score, large);
    }

    @ResponseBody
    @RequestMapping(value = "/score-all", method = RequestMethod.GET)
    public List<Score> getAllScore() {
        return scoreService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/emotion-all", method = RequestMethod.GET)
    public List<EmotionScore> getAllEmotion() {
        return emotionScoreService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/director-all", method = RequestMethod.GET)
    public HashMap<String, String> getAllDirector(@RequestParam int limit) {
        return directorService.findAll(limit);
    }

    @ResponseBody
    @RequestMapping(value = "/actor-all", method = RequestMethod.GET)
    public HashMap<String, Integer> getAllActor(@RequestParam int limit) {
        return actorService.findAll(limit);
    }

    @ResponseBody
    @RequestMapping(value = "/label-all", method = RequestMethod.GET)
    public HashMap<String, Integer> getAllLabel(@RequestParam int limit) {
        return labelService.findAll(limit);
    }
}
