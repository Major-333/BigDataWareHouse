package com.example.dw_backend.controller.mysql;

import com.example.dw_backend.service.mysql.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/director", produces = {MediaType.APPLICATION_JSON_VALUE})
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/director-name/{directorName}")
    public List<Map<String, String>> getDirectorMovieCount(@PathVariable String directorName) {
        return directorService.parsingDirectorMovie(directorName);
    }

}
