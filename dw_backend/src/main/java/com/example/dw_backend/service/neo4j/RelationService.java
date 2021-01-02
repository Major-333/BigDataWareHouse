package com.example.dw_backend.service.neo4j;
import com.example.dw_backend.dao.neo4j.RelationQuery;
import com.example.dw_backend.model.RelationReturn;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationService {

    private final RelationQuery relationQuery;

    public RelationService(RelationQuery relationQuery){
        this.relationQuery = relationQuery;
    }

    public RelationReturn generateRelation(final String startName, final String startLabel, final String endLabel){

        List<HashMap<String, String>> ans= new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        List<Record> recordList = relationQuery.findRelation(startName, startLabel, endLabel);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;

        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("name", record.get("name").toString());
            item.put("count", record.get("count").toString());
            ans.add(item);
        }

        RelationReturn relationReturn = new RelationReturn(time, ans);
        return relationReturn;
    }

    public static void main(String[] args) {
        RelationQuery query = new RelationQuery("bolt://localhost:7687", "neo4j", "your_password");
        RelationService service = new RelationService(query);
        service.generateRelation("Avery Brooks", "Actor", "Director");
    }
}
