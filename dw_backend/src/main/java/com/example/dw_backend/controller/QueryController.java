package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.Movie;
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
    public List<Movie> getDirectorMovieList(@RequestParam String directorName) {
        return directorService.parsingDirectorMovie(directorName);
    }

    /**
     * 查询给定演员的电影,返回电影列表
     *
     * @param actorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/actor", method = RequestMethod.GET)
    public List<Movie> getActorMovieList(@RequestParam String actorName) {
        return actorService.parsingActorMovie(actorName);
    }

    /**
     * 查询给定标签的电影,返回电影列表
     *
     * @param labelName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/label", method = RequestMethod.GET)
    public List<Movie> getLabelMovieList(@RequestParam String labelName) {
        return labelService.parsingLabelMovie(labelName);
    }


    @ResponseBody
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public List<Movie> getScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return movieService.parsingScoreList(score, comparison);
    }

    @ResponseBody
    @RequestMapping(value = "/emotion", method = RequestMethod.GET)
    public List<Movie> getEmotionScoreMovieList(@RequestParam int score, @RequestParam String comparison) {
        return movieService.parsingEmoScoreList(score, comparison);
    }

    @ResponseBody
    @RequestMapping(value = "/title", method = RequestMethod.GET)
    public List<Movie> getEmotionScoreMovieList(@RequestParam String title) {
        return movieService.parsingTitleList(title);
    }

}
