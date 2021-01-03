package com.example.dw_backend.dao.neo4j;

import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

public class SimpleQuery implements AutoCloseable{

    private final Driver driver;

    public SimpleQuery(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    /**
     * ② 按照电影名称进行查询电影
     */
    public List<Record> queryByTitle(final String title){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (a:Movie) where a.title=$title return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title limit 200;",
                            parameters("title", title));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    /**
     * ③ 按照导演进行查询电影
     */
    public List<Record>queryMovieByDirector (final String director){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (d:Director)-[]-(a:Movie) where d.director=$director return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title limit 200;",
                            parameters("director", director));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    /**
     * ④ 按照演员进行查询电影
     */
    public List<Record>queryMovieByActor (final String actor){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (d:Actor)-[]-(a:Movie) where d.actor=$actor return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title limit 200;",
                            parameters("actor", actor));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    /**
     * ⑤ 按照类别进行查询电影
     */
    public List<Record>queryMovieByLabel (final String label){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (d:Label)-[]-(a:Movie) where d.label=$label return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title limit 200;",
                            parameters("label", label));
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    /**
     * ⑥ 按照评价分数进行查询电影
     * ⑦ 按照情感分数进行查询电影
     * scoreType: score 评价分数 emotion_score 情感分数
     * cmp: 0 相等 1 greater -1 less
     */
    public List<Record>queryMovieByScore (final Integer score, final String scoreType, final String cmp){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    switch (cmp) {
                        case "equal":
                            result = transaction.run("match (a:Movie) where a." + scoreType + "=$score return " +
                                            "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                            "a.title as title limit 200;",
                                    parameters("score", Integer.valueOf(score.toString())));
                            break;
                        case "greater":
                            result = transaction.run("match (a:Movie) where a." + scoreType + ">"+score+" return " +
                                            "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                            "a.title as title limit 200;",
                                    parameters("score", Integer.valueOf(score.toString())));
                            break;
                        case "less":
                            String cmd = "match (a:Movie) where a." + scoreType + "<"+score+" return " +
                                    "a.product_id as product_id, a.score as score, a.emotion_score as emotion_score, " +
                                    "a.title as title limit 200;";
                            result = transaction.run(cmd);
                            break;
                        default:
                            return new ArrayList<Record>();
                    }
                    List<Record> resultList = result.list();
                    System.out.println(resultList);
                    return resultList;
                }
            });
            return ans;
        }
    }

    public static void main(String[] args) throws Exception {
        try (SimpleQuery query = new SimpleQuery("bolt://localhost:7687", "neo4j", "your_password")){
            List<Record> ans = query.queryByTitle("Dr. Seuss - The Cat in the Hat");
            System.out.println(ans);
        }
    };
}
