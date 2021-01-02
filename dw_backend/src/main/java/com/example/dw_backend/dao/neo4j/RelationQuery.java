package com.example.dw_backend.dao.neo4j;

import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class RelationQuery implements AutoCloseable{
    private final Driver driver;

    public RelationQuery(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    /**
     * ⑧⑨⑩(11) 按照演员（演员）和导演（演员）的关系进行查询及统计
     * 查询和某个演员合作最多的导演
     * @param: startName: 起始点名称 startLabel: 起始节点类别 endLabel: 结束点类别
     * @return: List<Record> , Record 包括 导演名和合作次数
     */
    public List<Record> findRelation(final String startName, final String startLabel, final String endLabel){
        try(Session session = driver.session()) {
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    if(startLabel.equals("Actor") && endLabel.equals("Actor")){
                        result = transaction.run("match (d:Actor)-[r:actor_actor]-(a:Actor) where a.actor=$data return d.actor as name, r.count as count;",
                                parameters("data", startName));
                    } else if (startLabel.equals("Actor") && endLabel.equals("Director")){
                        result = transaction.run("match (d:Director)-[r:actor_director]-(a:Actor) where a.actor=$data return d.director as name, r.count as count;",
                                parameters("data", startName));
                    } else if (startLabel.equals("Director") && endLabel.equals("Actor")){
                        result = transaction.run("match (d:Actor)-[r:actor_director]-(a:Director) where a.director=$data return d.actor as name, r.count as count;",
                                parameters("data", startName));
                    } else if (startLabel.equals("Director") && endLabel.equals("Director")){
                        result = transaction.run("match (d:Director)-[r:director_director]-(a:Director) where a.director=$data return d.director as name, r.count as count;",
                                parameters("data", startName));
                    } else {
                        return new ArrayList<>();
                    }
//                    result = transaction.run("match (d:"+endLabel+")-[]-(a:"+startLabel+") where a.actor=$actor return d.director as name, d.count as count",
//                            parameters("actor", startName));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    public static void main(String[] args) throws Exception {
        try (RelationQuery query = new RelationQuery("bolt://localhost:7687", "neo4j", "your_password")){
            List<Record> ans = query.findRelation("Avery Brooks","Actor","Director");
            System.out.println(ans);
        }
    };
}
