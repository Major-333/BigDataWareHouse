package com.example.dw_backend.controller;

import com.example.dw_backend.dao.neo4j.SimpleQuery;
import com.example.dw_backend.dao.neo4j.StatisticsQuery;
import com.example.dw_backend.dao.neo4j.TimeQuery;
import com.example.dw_backend.model.mysql.EmotionScore;
import com.example.dw_backend.model.mysql.Score;
import com.example.dw_backend.service.mysql.*;
import com.example.dw_backend.service.neo4j.SimpleQueryService;
import com.example.dw_backend.service.neo4j.StatisticsService;
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

    final private SimpleQueryService simpleQueryService = new SimpleQueryService(
            new SimpleQuery("bolt://localhost:7687", "neo4j", "your_password"),
            new TimeQuery("bolt://localhost:7687", "neo4j", "your_password")
    );

    final private StatisticsService statisticsService = new StatisticsService(
            new StatisticsQuery("bolt://localhost:7687", "neo4j", "your_password")
    );

    @ResponseBody
    @RequestMapping(value = "/mysql/time", method = RequestMethod.GET)
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
    @RequestMapping(value = "/mysql/emotion", method = RequestMethod.GET)
    public HashMap<String, Integer> getCountByEmotion(int score, String large) {
        return emotionScoreService.parserCount(score, large);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/score", method = RequestMethod.GET)
    public HashMap<String, Integer> getCountByScore(int score, String large) {
        return scoreService.parserCount(score, large);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/score-all", method = RequestMethod.GET)
    public List<Score> getAllScore() {
        return scoreService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/emotion-all", method = RequestMethod.GET)
    public List<EmotionScore> getAllEmotion() {
        return emotionScoreService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/director-all", method = RequestMethod.GET)
    public HashMap<String, String> getAllDirector(@RequestParam int limit) {
        return directorService.findAll(limit);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/actor-all", method = RequestMethod.GET)
    public HashMap<String, Integer> getAllActor(@RequestParam int limit) {
        return actorService.findAll(limit);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/label-all", method = RequestMethod.GET)
    public HashMap<String, Integer> getAllLabel(@RequestParam int limit) {
        return labelService.findAll(limit);
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/time", method = RequestMethod.GET)
    public HashMap<String, Object> getCountByTimeInNeo4j(@RequestParam String time, @RequestParam String type, @RequestParam String comparison) {
        return simpleQueryService.getTimeCount(time, type, comparison);
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/score-all", method = RequestMethod.GET)
    public HashMap<String, Object>  statisticsScoreInNeo4j() {
        return statisticsService.scoreStatistic("score");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/emotion-all", method = RequestMethod.GET)
    public HashMap<String, Object> statisticsEmotionScoreInNeo4j() {
        return statisticsService.scoreStatistic("emotion_score");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/score", method = RequestMethod.GET)
    public HashMap<String, Object> getScoreCountInNeo4j(@RequestParam Integer score, @RequestParam String comparison) {
        return statisticsService.getScoreCount(score,"score",comparison);
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/emotion", method = RequestMethod.GET)
    public HashMap<String, Object> getEmotionScoreCountInNeo4j(@RequestParam Integer score, @RequestParam String comparison) {
        return statisticsService.getScoreCount(score,"emotion_score",comparison);
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/actor-all", method = RequestMethod.GET)
    public HashMap<String, Object> getActorCountInNeo4j(@RequestParam String actor) {
        return statisticsService.getCountByType(actor, "actor");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/director-all", method = RequestMethod.GET)
    public HashMap<String, Object> getDirectorCountInNeo4j(@RequestParam String director) {
        return statisticsService.getCountByType(director, "director");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/label-all", method = RequestMethod.GET)
    public HashMap<String, Object> getLabelCountInNeo4j(@RequestParam String label) {
        return statisticsService.getCountByType(label, "label");
    }

}
