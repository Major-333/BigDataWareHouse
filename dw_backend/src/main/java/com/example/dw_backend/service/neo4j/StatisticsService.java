package com.example.dw_backend.service.neo4j;

import com.example.dw_backend.dao.neo4j.StatisticsQuery;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsService {

    private final StatisticsQuery statisticsQuery;

    public StatisticsService(StatisticsQuery statisticsQuery){
        this.statisticsQuery = statisticsQuery;
    }

    public HashMap<String, Object> getCountByType(final String data, final String nodeLabel){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        recordList = statisticsQuery.getCountByType(data, nodeLabel);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        if(recordList.size()>0){
            ret.put("count", recordList.get(0).get("count").toString());
        } else {
            ret.put("count", 0);
        }
        return ret;
    }

    public HashMap<String, Object> getScoreCount(Integer score, String scoreType, String cmp){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        recordList = statisticsQuery.getScoreCount(score, scoreType, cmp);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        if(recordList.size()>0){
            ret.put("count", recordList.get(0).get("count").toString());
        } else {
            ret.put("count", 0);
        }
        return ret;
    }

    public HashMap<String, Object> scoreStatistic(String scoreType){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        recordList = statisticsQuery.statisticScore(scoreType);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            if(record.get("score").toString().equals("NULL")){
                continue;
            }
            item.put("score", record.get("score").toString());
            item.put("count", record.get("count").toString());
            ans.add(item);
        }
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("statistics", ans);
        return ret;
    }

    public static void main(String[] args) {
        StatisticsService statisticsService = new StatisticsService(
                new StatisticsQuery("bolt://localhost:7687", "neo4j", "your_password")
        );
        System.out.println(statisticsService.getScoreCount(200,"score","less"));
        System.out.println(statisticsService.getCountByType("Movies & TV", "Label"));
        System.out.println(statisticsService.getScoreCount(50,"emotion_score","equal"));
    }



}
