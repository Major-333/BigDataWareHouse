package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.Actor;
import com.example.dw_backend.model.mysql.Director;
import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {

    /**
     * XX演员共有多少电影 返回电影列表
     *
     * @param actor
     * @return
     */
    @Query(value = "call find_actor_movie(:act);", nativeQuery = true)
    List<Actor> getMovieCount(@Param("act") String actor);

    /**
     * 根据演员名找合作过的导演
     *
     * @param actor
     * @return
     */
    @Query(value = "call find_director_by_actor(:act);", nativeQuery = true)
    List<Object> getDirectorList(@Param("act") String actor);

    /**
     * 根据演员查合作过的演员
     *
     * @param actor
     * @return
     */
    @Query(value = "call find_actor_by_actor(:act);", nativeQuery = true)
    List<Object> getActorList(@Param("act") String actor);


}
