package com.example.dw_backend.controller;

import com.example.dw_backend.dao.neo4j.SimpleQuery;
import com.example.dw_backend.dao.neo4j.TimeQuery;
import com.example.dw_backend.model.mysql.Movie;
//import com.example.dw_backend.model.mysql.returnValue.QueryReturn;
import com.example.dw_backend.model.QueryReturn;
import com.example.dw_backend.service.mysql.*;
import com.example.dw_backend.service.neo4j.SimpleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * @author xuedixuedi
 * 返回值都是MovieList
 */
@RestController
@RequestMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE})
public class QueryController {

    @Autowired
    private DirectorService directorService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private MovieService movieService;

    final private SimpleQueryService simpleQueryService = new SimpleQueryService(
            new SimpleQuery("bolt://localhost:7687", "neo4j", "your_password"),
            new TimeQuery("bolt://localhost:7687", "neo4j", "your_password")
    );

    /**
     * 查询给定导演的电影,返回电影列表
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mysql/director", method = RequestMethod.GET)
    public QueryReturn getDirectorMovieList(@RequestParam String directorName) {
        return new QueryReturn(directorService.getMovieTime(), directorService.parsingDirectorMovie(directorName));
    }

    /**
     * 查询给定演员的电影,返回电影列表
     *
     * @param actorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mysql/actor", method = RequestMethod.GET)
    public QueryReturn getActorMovieList(@RequestParam String actorName) {
        return new QueryReturn(actorService.getMovieTime(), actorService.parsingActorMovie(actorName));
    }

    /**
     * 查询给定标签的电影,返回电影列表
     *
     * @param labelName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mysql/label", method = RequestMethod.GET)
    public QueryReturn getLabelMovieList(@RequestParam String labelName) {
        return new QueryReturn(labelService.getMovieTime(), labelService.parsingLabelMovie(labelName));
    }


    @ResponseBody
    @RequestMapping(value = "/mysql/score", method = RequestMethod.GET)
    public QueryReturn getScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return new QueryReturn(movieService.getScoreTime(), movieService.parsingScoreList(score, comparison));
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/emotion", method = RequestMethod.GET)
    public QueryReturn getEmotionScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return new QueryReturn(movieService.getEmotionTime(), movieService.parsingEmoScoreList(score, comparison));
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/title", method = RequestMethod.GET)
    public QueryReturn getEmotionScoreMovieList(@RequestParam String title) {
        return new QueryReturn(movieService.getTitleTime(), movieService.parsingTitleList(title));
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/title", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByTitle(@RequestParam String title){
        return simpleQueryService.generateMovieList(title, "title");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/actor", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByActor(@RequestParam String actor){
        return simpleQueryService.generateMovieList(actor, "Actor");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/director", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByDirector(@RequestParam String director){
        return simpleQueryService.generateMovieList(director, "Director");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/label", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByLabel(@RequestParam String label){
        return simpleQueryService.generateMovieList(label, "Label");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/score", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByScore(@RequestParam Integer score, @RequestParam String comparison){
        System.out.println("1");
        return simpleQueryService.generateMovieListByScore(score,"score", comparison );
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/emotion", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByEmotion(@RequestParam Integer emotion, @RequestParam String comparison){
        return simpleQueryService.generateMovieListByScore(emotion,"emotion_score", comparison );
    }

}
