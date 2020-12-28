package com.example.dw_backend.controller;

import com.example.dw_backend.model.mysql.Movie;
import com.example.dw_backend.service.mysql.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {

    @Autowired
    private TimeService timeService;

    @ResponseBody
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public HashMap<String, Integer> getMovieListByTime(@RequestParam String time, @RequestParam String type, @RequestParam String comparison) {
        HashMap<String, Integer> result = new HashMap<>();
        String[] ymd = time.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);
        int season = day / 4 + 1;
        if (type.equals("year")) {
            result = timeService.parserYearCount(year, comparison);
        } else if (type.equals("month")) {
            result = timeService.parserMonthCount(year, month, comparison);
        } else if (type.equals("day")) {
            result = timeService.parserDayCount(year, month, day, comparison);
        } else if (type.equals("season")) {
            result = timeService.parserSeasonCount(year, season);
        }
        return result;
    }
}
