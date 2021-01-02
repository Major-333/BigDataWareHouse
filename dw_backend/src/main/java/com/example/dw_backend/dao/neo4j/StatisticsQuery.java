package com.example.dw_backend.dao.neo4j;

import org.neo4j.driver.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

public class StatisticsQuery implements AutoCloseable{

    private final Driver driver;

    public StatisticsQuery (String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public List<Record> statisticScore(final String scoreType){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (a:Movie) where a.title=$title return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title;",
                            parameters("title", title));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

}

//package com.example.dw_backend.dao.neo4j;
//
//        import org.neo4j.driver.*;
//
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//        import static org.neo4j.driver.Values.parameters;
//
//public class SimpleQuery implements AutoCloseable{
//
//    private final Driver driver;
//
//    public SimpleQuery(String uri, String user, String password){
//        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
//    }
//
//    @Override
//    public void close() throws Exception
//    {
//        driver.close();
//    }
//
//    /**
//     * ② 按照电影名称进行查询及统计
//     */
//    public String queryTitleCount(final String title){
//        try(Session session = driver.session()){
//            String titleCount = session.readTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction transaction) {
//                    Result result = transaction.run("match (a:Movie) where a.title=$title return a.version_count",
//                            parameters("title", title));
//                    List<Record> resultList = result.list();
//
//                    if(resultList.size()==0){
//                        return "";
//                    } else {
//                        return resultList.get(0).get(result.keys().get(0)).toString();
//                    }
//
//                }
//            });
//            return titleCount;
//        }
//    }
//
//    /**
//     * ③ 按照导演进行查询及统计
//     */
//    public String queryDirectorCount (final String director){
//        try(Session session = driver.session()){
//            String directorCount = session.readTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction transaction) {
//                    Result result = transaction.run("match (a:Director) where a.director=$director return a.count;",
//                            parameters("director", director));
//                    List<Record> resultList = result.list();
//
//                    if(resultList.size() == 0){
//                        return "";
//                    } else {
//                        return resultList.get(0).get(result.keys().get(0)).toString();
//                    }
//                }
//            });
//            return directorCount;
//        }
//    }
//
//    /**
//     * ④ 按照演员进行查询及统计
//     */
//    public String queryActorCount (final String actor, final String nodeLabel){
//        try(Session session = driver.session()){
//            String actorCount = session.readTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction transaction) {
//                    Map<String,Object> params = new HashMap<>();
//                    params.put( "actor", actor );
//                    params.put("nodeLabel", nodeLabel);
//
//                    Result result = transaction.run("match (a: Actor) where a.actor = $actor return a.count;",
//                            params);
//                    List<Record> resultList = result.list();
//                    if(resultList.size() == 0){
//                        return "";
//                    } else {
//                        return resultList.get(0).get(result.keys().get(0)).toString();
//                    }
//                }
//            });
//            return actorCount;
//        }
//    }
//
//    /**
//     * ⑤ 按照演员和导演的关系进行查询及统计
//     */
//    public String findDirectorByActor(final String actor){
//        return "TODO";
//    }
//
//    /**
//     * ⑥ 按照演员和导演的关系进行查询及统计
//     */
//    public String queryLabelCount (final String label){
//        try(Session session = driver.session()){
//            String labelCount = session.readTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction transaction) {
//                    Result result = transaction.run("match (a:Label) where a.label=$label return a.count;",
//                            parameters("label", label));
//                    List<Record> resultList = result.list();
//
//                    if(resultList.size() == 0){
//                        return "";
//                    } else {
//                        return resultList.get(0).get(result.keys().get(0)).toString();
//                    }
//                }
//            });
//            return labelCount;
//        }
//    }
//
//    /**
//     * ⑦ 按照用户评价进行查询及统计
//     * 按照评价分数
//     * scoreType : 0 普通分数 1 情感分数
//     * cmp: 0 相等 1 after -1 before
//     */
//    public String queryScoreCount(final String score, final Integer scoreType, final Integer cmp){
//        try(Session session = driver.session()){
//            String cnt = session.readTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction transaction) {
//                    Result result;
//                    if(cmp == 0){
//                        result = transaction.run("match (m:Movie) where m.score=$score return count(*)",
//                                parameters("score", Integer.valueOf(score)));
//                    } else if(cmp == 1){
//                        result = transaction.run("match (m:Movie) where m.score>$score return count(*)",
//                                parameters("score", Integer.valueOf(score)));
//                    } else if(cmp == -1){
//                        result = transaction.run("match (m:Movie) where m.score<$score return count(*)",
//                                parameters("score", Integer.valueOf(score)));
//                    } else {
//                        return "";
//                    }
//                    List<Record> resultList = result.list();
//                    if(resultList.size()==0){
//                        return "";
//                    } else {
//                        return resultList.get(0).get(result.keys().get(0)).toString();
//                    }
//                }
//            });
//            return cnt;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        try (SimpleQuery query = new SimpleQuery("bolt://localhost:7687", "neo4j", "your_password")){
//            String cnt = query.queryActorCount("Shannon Tweed", "Actor");
//            System.out.println(cnt);
//            cnt = query.queryScoreCount("400", 0, -1);
//            System.out.println(cnt);
//        }
//    };
//}
