package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.Movie;
import com.example.dw_backend.model.mysql.returnValue.QueryReturn;
import com.example.dw_backend.service.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 查询给定导演的电影,返回电影列表
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/director", method = RequestMethod.GET)
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
    @RequestMapping(value = "/actor", method = RequestMethod.GET)
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
    @RequestMapping(value = "/label", method = RequestMethod.GET)
    public QueryReturn getLabelMovieList(@RequestParam String labelName) {
        return new QueryReturn(labelService.getMovieTime(), labelService.parsingLabelMovie(labelName));
    }


    @ResponseBody
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public QueryReturn getScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return new QueryReturn(movieService.getScoreTime(), movieService.parsingScoreList(score, comparison));
    }

    @ResponseBody
    @RequestMapping(value = "/emotion", method = RequestMethod.GET)
    public QueryReturn getEmotionScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return new QueryReturn(movieService.getEmotionTime(), movieService.parsingEmoScoreList(score, comparison));
    }

    @ResponseBody
    @RequestMapping(value = "/title", method = RequestMethod.GET)
    public QueryReturn getEmotionScoreMovieList(@RequestParam String title) {
        return new QueryReturn(movieService.getTitleTime(), movieService.parsingTitleList(title));
    }

}
