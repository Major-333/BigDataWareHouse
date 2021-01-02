package com.example.dw_backend.service.neo4j;

import com.example.dw_backend.dao.neo4j.RelationQuery;
import com.example.dw_backend.dao.neo4j.SimpleQuery;
import com.example.dw_backend.dao.neo4j.TimeQuery;
import com.example.dw_backend.model.QueryReturn;
import com.example.dw_backend.model.RelationReturn;
import com.example.dw_backend.model.mysql.Time;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SimpleQueryService {

    private final SimpleQuery simpleQuery;
    private final TimeQuery timeQuery;

    public SimpleQueryService (SimpleQuery simpleQuery, TimeQuery timeQuery){
        this.simpleQuery = simpleQuery;
        this.timeQuery = timeQuery;
    }

    public HashMap<String, Object> generateMovieListByScore(Integer score, String scoreType, String cmp){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        recordList = simpleQuery.queryMovieByScore(score, scoreType, cmp);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("product_id", record.get("product_id").toString());
            item.put("score", record.get("score").toString());
            item.put("emotion_score", record.get("emotion_score").toString());
            item.put("title", record.get("title").toString());
            ans.add(item);
        }
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("movieList", ans);
        return ret;
    }

    public HashMap<String, Object> generateMovieList(String data, String queryType){

        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        switch (queryType) {
            case "title":
            case "Title":
                recordList = simpleQuery.queryByTitle(data);
                break;
            case "actor":
            case "Actor":
                recordList = simpleQuery.queryMovieByActor(data);
                break;
            case "director":
            case "Director":
                recordList = simpleQuery.queryMovieByDirector(data);
                break;
            case "label":
            case "Label":
                recordList = simpleQuery.queryMovieByLabel(data);
                break;
            default:
                System.out.println("error");
                return new HashMap<String, Object>();
        }

        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;

        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("product_id", record.get("product_id").toString());
            item.put("score", record.get("score").toString());
            item.put("emotion_score", record.get("emotion_score").toString());
            item.put("title", record.get("title").toString());
            ans.add(item);
        }
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("movieList", ans);
        return ret;
    }

    public HashMap<String, Object> getTimeCount(String timeData, String timeType, String cmp){

        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        List<String> dataList;
        long startTime;    //获取开始时间
        switch (timeType){
            case "year":
                startTime = System.currentTimeMillis();
                recordList = timeQuery.queryTimeByYear(timeData, cmp);
                break;
            case "season":
                dataList = Arrays.asList(timeData.split("-"));
                System.out.println(dataList);
                int month = Integer.parseInt(dataList.get(1));
                Integer season;
                if (month<=3){
                    season = 1;
                } else if (month<=6){
                    season = 2;
                } else if (month<=9) {
                    season = 3;
                } else if (month<=12){
                    season = 4;
                } else {
                    System.out.println("error");
                    season = -1;
                }
                startTime = System.currentTimeMillis();
                recordList = timeQuery.queryTimeBySeason(dataList.get(0), season.toString(), cmp);
                break;
            case "month":
                dataList = Arrays.asList(timeData.split("-"));
                startTime = System.currentTimeMillis();
                recordList = timeQuery.queryTimeByMonth(dataList.get(0), dataList.get(1), cmp);
                break;
            case "day":
                dataList = Arrays.asList(timeData.split("-"));
                startTime = System.currentTimeMillis();
                recordList = timeQuery.queryTimeByDay(dataList.get(0), dataList.get(1), dataList.get(2), cmp);
                break;
            default:
                System.out.println("error");
                return new HashMap<>();
        }
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("count", recordList.get(0).get("count").toString());
        return ret;
    }

    public static void main(String[] args) {
        SimpleQuery query = new SimpleQuery("bolt://localhost:7687", "neo4j", "your_password");
        TimeQuery timeQuery = new TimeQuery("bolt://localhost:7687", "neo4j", "your_password");
        SimpleQueryService service = new SimpleQueryService(query, timeQuery);
        System.out.println(service.getTimeCount("2000-10","month","greater"));
        System.out.println(service.generateMovieList("Dr. Seuss - The Cat in the Hat", "title"));
    }
}
