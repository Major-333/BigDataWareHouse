package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.Director;
import com.example.dw_backend.service.mysql.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/director", produces = {MediaType.APPLICATION_JSON_VALUE})
public class QueryController {

    @Autowired
    private DirectorService directorService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<HashMap<String, String>> getDirectorMovieCount(@RequestParam String directorName) {
        return directorService.parsingDirectorMovie(directorName);
    }

    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public List<Director> getDirectorByCount(@RequestParam String count) {
        return directorService.parsingDirectorCount(count);
    }


}
