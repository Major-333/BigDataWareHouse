package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.Director;
import com.example.dw_backend.model.mysql.Movie;
import com.example.dw_backend.service.mysql.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    /**
     * 查询给定导演的电影,返回电影列表
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/director", method = RequestMethod.GET)
    public List<Movie> getDirectorMovieCount(@RequestParam String directorName) {
        return directorService.parsingDirectorMovie(directorName);
    }


}
