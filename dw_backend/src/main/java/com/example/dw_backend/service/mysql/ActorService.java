package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.ActorRepository;
import com.example.dw_backend.model.mysql.Actor;
import com.example.dw_backend.model.mysql.Director;
import com.example.dw_backend.model.mysql.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private long directorTime;
    private long actorTime;
    private long movieTime;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    /**
     * 把查询返回值变成MovieList类型
     *
     * @param actor
     * @return
     */
    public List<Movie> parsingActorMovie(String actor) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        List<Actor> actorList = actorRepository.getMovieCount(actor);
        long endTime = System.currentTimeMillis();    //获取开始时间
        this.movieTime = endTime - startTime;

        List<Movie> movieList = new ArrayList<>();
        for (Actor act : actorList) {
            movieList.add(act.getMovie());
        }
        return movieList;
    }


    public List<HashMap<String, String>> parsingGetActorList(String actor) {

        List<HashMap<String, String>> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        List<Object> actorList = this.actorRepository.getActorList(actor);
        long endTime = System.currentTimeMillis();    //获取开始时间
        this.actorTime = endTime - startTime;


        for (Object row : actorList) {
            HashMap<String, String> temp1 = new HashMap<>();
            Object[] cells = (Object[]) row;
            temp1.put("actorName", String.valueOf(cells[0]));
            temp1.put("productId", String.valueOf(cells[1]));
            result.add(temp1);
        }
        return result;
    }

    public List<HashMap<String, String>> parsingGetDirectorList(String actor) {

        List<HashMap<String, String>> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        List<Object> directorList = this.actorRepository.getDirectorList(actor);
        long endTime = System.currentTimeMillis();    //获取开始时间
        this.directorTime = endTime - startTime;

        for (Object row : directorList) {
            HashMap<String, String> temp1 = new HashMap<>();
            Object[] cells = (Object[]) row;
            temp1.put("directorName", String.valueOf(cells[0]));
            temp1.put("productId", String.valueOf(cells[1]));
            result.add(temp1);
        }
        return result;
    }

    public HashMap<String, Integer> findAll(int limit) {
        List<Actor> actorList = this.actorRepository.findAll();
        HashMap<String, Integer> temp1 = new HashMap<>();
        for (int i = 0; i < actorList.size(); i++) {
            String name = actorList.get(i).getActorName();
            int count = actorList.get(i).getMovieCount();
            temp1.put(name, count);
            if (temp1.size() >= limit) {
                return temp1;
            }
        }
        return temp1;
    }

    public long getDirectorTime() {
        return directorTime;
    }

    public long getActorTime() {
        return actorTime;
    }

    public long getMovieTime() {
        return movieTime;
    }
}
