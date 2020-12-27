package com.example.dw_backend.controller.mysql;

import com.example.dw_backend.service.mysql.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/director", produces = {MediaType.APPLICATION_JSON_VALUE})
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, String>> getDirectorMovieCount(@RequestParam String directorName) {
        return directorService.parsingDirectorMovie(directorName);
    }

}
