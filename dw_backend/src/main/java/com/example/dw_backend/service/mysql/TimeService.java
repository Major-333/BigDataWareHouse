package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.TimeRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public HashMap<String, Integer> parserYearCount(int year, String after) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = timeRepository.getMovieCountByYear(year, after).get(0);
        map.put("Count", result);
        return map;
    }

    public HashMap<String, Integer> parserMonthCount(int year, int month, String after) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = timeRepository.getMovieCountByMonth(year, month, after).get(0);
        System.out.println(result);
        map.put("Count", result);
        return map;
    }

    public HashMap<String, Integer> parserDayCount(int year, int month, int day, String after) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = timeRepository.getMovieCountByDay(year, month, day, after).get(0);
        System.out.println(result);
        map.put("Count", result);
        return map;
    }

    public HashMap<String, Integer> parserSeasonCount(int year, int season) {
        HashMap<String, Integer> map = new HashMap<>();
        int result = timeRepository.getMovieCountBySeason(year, season).get(0);
        System.out.println(result);
        map.put("Count", result);
        return map;
    }

}
